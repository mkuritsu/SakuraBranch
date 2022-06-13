package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MenuComponent {

    private ItemStack item;

    public MenuComponent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    abstract void handleClick(InventoryClickEvent event, Player player, Menu gui);

}
