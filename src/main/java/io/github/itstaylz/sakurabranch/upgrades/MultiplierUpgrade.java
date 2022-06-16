package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import io.github.itstaylz.sakurabranch.SakuraBranch;
import io.github.itstaylz.sakurabranch.utils.CropUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class MultiplierUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public MultiplierUpgrade() {
        super("sakurabench_upgrade_multiplier", "Multiplier", SakuraBranch.getPluginConfig().getUpgradePrices("multiplier"), BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        double multiplier = SakuraBranch.getPluginConfig().getMultiplierAmount(upgradeLevel);
        CropUtils.giveCropXP(player, event.getBlockState(), multiplier);
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/253120a80d315725eb2d2ff43d1ea562fa39dea3844c7f178f94310b0ddab4e5")
                .setDisplayName(StringUtils.colorize("&d&l" + getName()))
                .setLore("", StringUtils.colorize("&7Multiply the amount of &bXp &7gained!"));
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        double multiplier = 1;
        if (currentLevel != 0)
            multiplier = SakuraBranch.getPluginConfig().getMultiplierAmount(currentLevel);
        builder.addLore(StringUtils.colorize("&7Current: &b" + multiplier + "x"));
        if (currentLevel < getMaxLevel()) {
            double nextMultiplier = SakuraBranch.getPluginConfig().getMultiplierAmount(currentLevel+1);
            builder.addLore(StringUtils.colorize("&7Next: &b" + nextMultiplier + "x"));
        }
        addDefaultLore(builder, hoe);
        return builder.addLore("").build();
    }
}
