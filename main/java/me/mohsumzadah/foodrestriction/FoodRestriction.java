package me.mohsumzadah.foodrestriction;

import me.mohsumzadah.foodrestriction.Commands.CommandRestrict;
import me.mohsumzadah.foodrestriction.Events.PlayerInteract;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

public final class FoodRestriction extends JavaPlugin {

    private static FoodRestriction instance;

    public static final String DATA_FILE = "data.yml";
    public static final String CONFIG_FILE = "config.yml";
    public static final String FOODS_FILE = "all_foods.yml";

    private FileConfiguration data;
    private FileConfiguration config;
    private FileConfiguration foods;

    public List<String> foodList;

    @Override
    public void onEnable() {
        instance = this;

        data = loadConfiguration(DATA_FILE);
        config = loadConfiguration(CONFIG_FILE);
        foods = loadConfiguration(FOODS_FILE);

        foodList = foods.getStringList("foods");

        CommandRestrict commandRestrict = new CommandRestrict();
        getCommand("restrict").setExecutor(commandRestrict);
        getCommand("restrict").setTabCompleter(commandRestrict);

        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);

        getLogger().info("FoodRestriction plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("FoodRestriction plugin disabled.");
    }

    private FileConfiguration loadConfiguration(String fileName) {
        File file = new File(getDataFolder(), fileName);

        if (!Files.exists(file.toPath())) {
            try {
                Files.createDirectories(file.getParentFile().toPath());
                saveResource(fileName, false);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to create " + fileName, e);
            }
        }

        FileConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().log(Level.SEVERE, "Failed to load " + fileName, e);
        }

        return configuration;
    }

    public void saveData() {
        saveFile(data, new File(getDataFolder(), DATA_FILE));
    }

    private void saveFile(FileConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save file: " + file.getName(), e);
        }
    }

    public static FoodRestriction getInstance() {
        return instance;
    }

    public FileConfiguration getData() {
        return data;
    }

    public FileConfiguration getConfigFile() {
        return config;
    }

    public FileConfiguration getFoods() {
        return foods;
    }
}
