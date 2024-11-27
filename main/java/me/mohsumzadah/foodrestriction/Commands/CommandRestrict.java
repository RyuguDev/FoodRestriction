package me.mohsumzadah.foodrestriction.Commands;

import me.mohsumzadah.foodrestriction.FoodRestriction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandRestrict implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        if (!sender.hasPermission("restrict.use")) {
            sender.sendMessage("You don't have permission to use this command.");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage("Usage: /restrict <player> <vegan/carnivores/omnivores/custom>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        String restrictionType = args[1].toLowerCase();

        if (restrictionType.equalsIgnoreCase("omnivorous")) {
            FoodRestriction.plugin.data.set("PLAYERS." + target.getUniqueId().toString(), null);
            FoodRestriction.plugin.saveData();
            sender.sendMessage("Player " + target.getName() + " is no longer restricted.");
        } else if (FoodRestriction.plugin.config.contains(restrictionType)) {
            FoodRestriction.plugin.data.set("PLAYERS." + target.getUniqueId().toString(), restrictionType);
            FoodRestriction.plugin.saveData();
            sender.sendMessage("Player " + target.getName() + " is now restricted to " + restrictionType + " foods.");
        } else {
            sender.sendMessage("Invalid restriction type. Valid options are: " + String.join(", ", FoodRestriction.plugin.config.getKeys(false)));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return null;
        if (!sender.hasPermission("restrict.use")) return null;

        if (args.length == 2) {
            List<String> types = new ArrayList<>(FoodRestriction.plugin.config.getKeys(false));
            types.add("omnivorous");
            return types;
        }

        return null;
    }
}
