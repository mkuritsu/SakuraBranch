package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class Menu implements InventoryHolder {

    private final Inventory inventory;
    private final HashMap<Integer, MenuComponent> contents = new HashMap<>();

    public Menu(int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void addComponent(int slot, MenuComponent component) {
        this.contents.put(slot, component);
        this.inventory.setItem(slot, component.getItem());
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    void handleClick(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        MenuComponent component = this.contents.get(slot);
        if (component != null)
            component.handleClick(event, player, this);
    }
}
