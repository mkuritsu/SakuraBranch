package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menus.Menu;
import io.github.itstaylz.hexlib.menus.components.MenuButton;
import io.github.itstaylz.hexlib.menus.components.MenuItem;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrade;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class HoeMenu extends Menu {

    private static final ItemStack PURPLE_GLASS = new ItemBuilder(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE))
            .setDisplayName(" ")
            .build();

    private static final ItemStack CYAN_GLASS = new ItemBuilder(new ItemStack(Material.CYAN_STAINED_GLASS_PANE))
            .setDisplayName(" ")
            .build();

    private static final ItemStack WHITE_GLASS = new ItemBuilder(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))
            .setDisplayName(" ")
            .build();

    private final ItemStack hoe;

    public HoeMenu(ItemStack hoe) {
        super(5*9, StringUtils.colorize("&d🌸 &dBranch &bUpgrader &d🌸"));
        this.hoe = hoe;
        addGlassPanels();
        addUpgradeToMenu(19, HoeUpgrades.AUTO_SELL);
        addUpgradeToMenu(21, HoeUpgrades.AUTO_PICKUP);
        addUpgradeToMenu(23, HoeUpgrades.AUTO_PLANT);
        addUpgradeToMenu(25, HoeUpgrades.MULTIPLIER);
    }

    private void addGlassPanels() {
        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0)
                addComponent(i, new MenuItem(PURPLE_GLASS));
            else
                addComponent(i, new MenuItem(CYAN_GLASS));
        }
        for (int i = 9; i < 36; i++) {
            addComponent(i, new MenuItem(WHITE_GLASS));
        }
        for (int i = 36; i < 45; i++) {
            if (i % 2 == 0)
                addComponent(i, new MenuItem(PURPLE_GLASS));
            else
                addComponent(i, new MenuItem(CYAN_GLASS));
        }
    }

    private void addUpgradeToMenu(int slot, HoeUpgrade<?> upgrade) {
        ItemStack menuItem = upgrade.getMenuItem(this.hoe);
        addComponent(slot, new MenuButton(menuItem, ((event, player, gui) -> {
            if (event.getAction() == InventoryAction.PICKUP_ALL) {
                if (HoeManager.canBuy(this.hoe, upgrade))
                    HoeManager.buyUpgrade(this.hoe, player, upgrade);
                else {
                    player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &cYou can't buy this upgrade!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }
                player.closeInventory();
            } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                if (HoeManager.canRefund(this.hoe, upgrade)) {
                    HoeManager.refundUpgrade(this.hoe, player, upgrade);
                }
                else {
                    player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &cYou can't refund this upgrade!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }
                player.closeInventory();
            }
        })));
    }
}
