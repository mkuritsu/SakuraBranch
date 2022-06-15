package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MultiplierUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    // TODO make this
    public MultiplierUpgrade() {
        super("sakurabench_upgrade_multiplier", "Multiplier", Arrays.asList(20000, 25000, 30000, 40000), BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {

    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/253120a80d315725eb2d2ff43d1ea562fa39dea3844c7f178f94310b0ddab4e5")
                .setDisplayName(StringUtils.colorize("&d&l" + getName()))
                .setLore("", StringUtils.colorize("&7Multiply the amount of &bXp &7gained!"));
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        int multiplier = currentLevel + 1;
        builder.addLore(StringUtils.colorize("&7Current: &b" + multiplier + "x"));
        if (currentLevel == getMaxLevel()) {
            builder.addLore(StringUtils.colorize("&a&lMAX LEVEL REACHED!"));
        } else {
            builder.addLore(StringUtils.colorize("&7Next: &b" + (multiplier+1) + "x"),
                    StringUtils.colorize("&7Price: &a$" + getPrice(multiplier)));
        }
        if (currentLevel > 0)
            builder.addLore(StringUtils.colorize("&7Right-Click to &cRefund"));
        return builder.addLore("").build();
    }
}
