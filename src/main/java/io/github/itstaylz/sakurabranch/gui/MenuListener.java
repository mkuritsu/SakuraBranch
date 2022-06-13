package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() != null && event.getClickedInventory().getHolder() instanceof Menu menu) {
                menu.handleClick(event, player);
            }
        }
    }
}
