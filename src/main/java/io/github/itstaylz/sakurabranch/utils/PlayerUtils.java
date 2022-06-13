package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class PlayerUtils {

    public static boolean canPickup(Player player, ItemStack item) {
        for (ItemStack slot : player.getInventory().getStorageContents()) {
            if (slot == null)
                return true;
            if (slot.isSimilar(item) && slot.getMaxStackSize() - slot.getAmount() >= item.getAmount()) {
                return true;
            }
        }
        return false;
    }
}
