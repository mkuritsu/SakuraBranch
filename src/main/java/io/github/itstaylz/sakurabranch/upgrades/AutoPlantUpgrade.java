package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import io.github.itstaylz.sakurabranch.utils.CropUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AutoPlantUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoPlantUpgrade() {
        super("sakurabranch_upgrade_autoplant", "Auto Plant", Arrays.asList(30000), BlockDropItemEvent.class);
    }

    // TODO remake to remove crop from inv
    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        Material type = event.getBlockState().getType();
        if (!CropUtils.isCrop(type))
            return;
        BlockState state = event.getBlockState();
        BlockData data = state.getBlockData();
        if (data instanceof Ageable ageable) {
            if (ageable.getAge() < ageable.getMaximumAge())
                event.getItems().clear();
        }
        event.getBlock().setType(type);
        player.playSound(player.getLocation(), Sound.ITEM_CROP_PLANT, 1f, 1f);
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/9bb9f7258408a90bc580d3e04c2367372e06123a4c0f54b79de765a8574b7c93")
                .setDisplayName(StringUtils.colorize("&b&l" + getName()))
                .setLore("", StringUtils.colorize("&7Automatically replants the same crop you harvested!"));
        if (currentLevel == getMaxLevel()) {
            builder.addLore(StringUtils.colorize("&a&lMAX LEVEL REACHED!"));
            builder.addLore(StringUtils.colorize("&7Right-Click to &cRefund"));
        } else {
            builder.addLore(StringUtils.colorize("&7Price: &a$" + getPrice(currentLevel+1)));
            builder.addLore(StringUtils.colorize("&7Left-Click to &aBuy"));
        }
        builder.addLore("", StringUtils.colorize("&cYou must have the seed in your inventory for this to work!"));
        return builder.addLore("").build();
    }
}
