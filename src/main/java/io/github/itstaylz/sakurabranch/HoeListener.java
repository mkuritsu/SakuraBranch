package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrade;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import io.github.itstaylz.sakurabranch.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class HoeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBlockDrop(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        triggerUpgrades(event, player, item);
    }

    @EventHandler
    private void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null && HoeManager.isSakuraBenchHoe(item) && event.getAction().name().contains("RIGHT")) {
            HoeMenu menu = new HoeMenu();
            menu.open(player);
        }
    }

    private <T extends Event> void triggerUpgrades(T event, Player player, ItemStack item) {
        Set<HoeUpgrade<?>> upgrades = HoeUpgrades.getUpgrades(event.getClass());
        if (upgrades == null)
            return;
        for (HoeUpgrade<?> upgrade : upgrades) {
            int level = HoeManager.getUpgradeLevel(item, upgrade);
            if (level > 0)
                ((HoeUpgrade<T>)upgrade).onTrigger(event, player, item, level);
        }
    }
}
