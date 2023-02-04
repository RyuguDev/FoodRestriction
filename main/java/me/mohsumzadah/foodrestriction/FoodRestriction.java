package me.mohsumzadah.foodrestriction;

import me.mohsumzadah.foodrestriction.Commands.CommandRestrict;
import me.mohsumzadah.foodrestriction.Events.PlayerInteract;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class FoodRestriction extends JavaPlugin {

    public static FoodRestriction plugin;
    public List<String> foodList;

    @Override
    public void onEnable() {
        plugin = this;

        createConfig();
        createData();
        createFoods();

        foodList = foods.getStringList("foods");


        getCommand("restrict").setExecutor(new CommandRestrict());
        getCommand("restrict").setTabCompleter(new CommandRestrict());
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);

    }

    private File dataF;
    public FileConfiguration data;

    private void createData(){
        dataF = new File(getDataFolder(), "data.yml");
        if(!dataF.exists()){
            dataF.getParentFile().mkdirs();
            saveResource("data.yml",false);
        }
        data = new YamlConfiguration();

        try {
            data.load(dataF);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }
    public void saveData(){
        try {
            data.save(dataF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FileConfiguration config;

    private void createConfig(){
        File configf = new File(getDataFolder(), "config.yml");
        if(!configf.exists()){
            configf.getParentFile().mkdirs();
            saveResource("config.yml",false);
        }
        config = new YamlConfiguration();

        try {
            config.load(configf);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public FileConfiguration foods;

    private void createFoods(){
        File foodsF = new File(getDataFolder(), "all_foods.yml");
        if(!foodsF.exists()){
            foodsF.getParentFile().mkdirs();
            saveResource("all_foods.yml",false);
        }
        foods = new YamlConfiguration();

        try {
            foods.load(foodsF);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

}
