package me.mohsumzadah.foodrestriction.Events;

import me.mohsumzadah.foodrestriction.FoodRestriction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player player = event.getPlayer();


            if (FoodRestriction.plugin.data.get("PLAYERS." + player.getUniqueId()) == null) {
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            String name = item.getType().toString().toLowerCase(Locale.ROOT);

            if (!FoodRestriction.plugin.foodList.contains(name)) return;

            String playerType = FoodRestriction.plugin.data.getString("PLAYERS." + player.getUniqueId());
            if (!FoodRestriction.plugin.config.getStringList(playerType).contains(name)){
                player.sendMessage("You can't eat that, it's not " + playerType+ " food.");
                event.setCancelled(true);
            }
        }
    }


}
