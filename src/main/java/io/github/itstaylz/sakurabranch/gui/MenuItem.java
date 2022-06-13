package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuItem extends MenuComponent {

    public MenuItem(ItemStack item) {
        super(item);
    }

    @Override
    void handleClick(InventoryClickEvent event, Player player, Menu gui) {
        event.setCancelled(true);
    }
}
