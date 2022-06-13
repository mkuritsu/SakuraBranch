package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.sakurabranch.gui.Menu;
import io.github.itstaylz.sakurabranch.gui.MenuButton;
import io.github.itstaylz.sakurabranch.gui.MenuItem;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import io.github.itstaylz.sakurabranch.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HoeMenu extends Menu {

    private static final ItemStack PURPLE_GLASS = new ItemBuilder(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE))
            .setDisplayName("")
            .build();

    private static final ItemStack CYAN_GLASS = new ItemBuilder(new ItemStack(Material.CYAN_STAINED_GLASS_PANE))
            .setDisplayName("")
            .build();

    private static final ItemStack WHITE_GLASS = new ItemBuilder(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))
            .setDisplayName("")
            .build();

    public HoeMenu() {
        super(5*9, "&d🌸 &dBranch &bUpgrader &d🌸");
        addGlassPanels();
        addComponent(19, new MenuButton(HoeUpgrades.AUTO_SELL.getMenuSkull(), ((event, player, gui) -> {
            player.closeInventory();
            player.sendMessage("SELLLLL!");
        })));
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
}
