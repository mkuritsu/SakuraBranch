package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuButton extends MenuComponent {

    private MenuButtonClick onClickEvent;

    public MenuButton(ItemStack item, MenuButtonClick onClick) {
        super(item);
        this.onClickEvent = onClick;
    }

    @Override
    void handleClick(InventoryClickEvent event, Player player, Menu gui) {
        event.setCancelled(true);
        this.onClickEvent.onClick(event, player, gui);
    }
}
