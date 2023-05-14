package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menu.Menu;
import io.github.itstaylz.hexlib.menu.MenuSettings;
import io.github.itstaylz.hexlib.menu.components.Button;
import io.github.itstaylz.hexlib.menu.components.Label;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrade;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class HoeMenu extends Menu {

    private static final MenuSettings SETTINGS = MenuSettings.builder()
            .withNumberOfRows(5)
            .withTitle(StringUtils.colorize("&d❀ &dBranch &bUpgrader &d❀"))
            .build();

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
        super(SETTINGS);
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
                setComponent(i, new Label(PURPLE_GLASS));
            else
                setComponent(i, new Label(CYAN_GLASS));
        }
        for (int i = 9; i < 36; i++) {
            setComponent(i, new Label(WHITE_GLASS));
        }
        for (int i = 36; i < 45; i++) {
            if (i % 2 == 0)
                setComponent(i, new Label(PURPLE_GLASS));
            else
                setComponent(i, new Label(CYAN_GLASS));
        }
    }

    private void addUpgradeToMenu(int slot, HoeUpgrade<?> upgrade) {
        ItemStack menuItem = upgrade.getMenuItem(this.hoe);
        setComponent(slot, new Button(menuItem, (menu, player, event) -> {
            if (event.getAction() == InventoryAction.PICKUP_ALL) {
                if (HoeManager.canBuy(this.hoe, upgrade))
                    HoeManager.buyUpgrade(this.hoe, player, upgrade);
                else {

                    player.sendMessage(StringUtils.colorize(SakuraBranch.getMessagesConfig().getMessage("cannot_apply_message")));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }
                player.closeInventory();
            } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                if (HoeManager.canRefund(this.hoe, upgrade)) {
                    HoeManager.refundUpgrade(this.hoe, player, upgrade);
                }
                else {
                    player.sendMessage(StringUtils.colorize(SakuraBranch.getMessagesConfig().getMessage("cannot_refund_message")));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }
                player.closeInventory();
            }
        }));
    }
}
