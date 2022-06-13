package io.github.itstaylz.sakurabranch.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface MenuButtonClick {

    void onClick(InventoryClickEvent event, Player player, Menu gui);
}
