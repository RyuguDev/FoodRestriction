package me.mohsumzadah.foodrestriction.Events;

import me.mohsumzadah.foodrestriction.FoodRestriction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        String playerType = FoodRestriction.plugin.data.getString("PLAYERS." + playerUUID);

        if (playerType == null) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;

        String itemName = item.getType().toString().toLowerCase(Locale.ROOT);

        if (!FoodRestriction.plugin.foodList.contains(itemName)) return;

        if (!FoodRestriction.plugin.config.getStringList(playerType).contains(itemName)) {
            player.sendMessage("You can't eat that; it's not " + playerType + " food.");
            event.setCancelled(true);
        }
    }
}
