package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.SakuraBranch;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class AutoPlantUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoPlantUpgrade() {
        super("sakurabranch_upgrade_autoplant", "Auto Plant", SakuraBranch.getPluginConfig().getUpgradePrices("auto_plant"), BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        Material type = event.getBlockState().getType();
        Material seeds = SakuraBranch.getPluginConfig().getSeeds(type);
        if (seeds != Material.AIR && player.getInventory().contains(seeds)) {
            for (ItemStack item : player.getInventory().getStorageContents()) {
                if (item != null && item.getType() == seeds) {
                    if (item.getAmount() > 1)
                        item.setAmount(item.getAmount() - 1);
                    else
                        player.getInventory().remove(item);
                    break;
                }
            }
            event.getBlock().setType(type);
            player.playSound(player.getLocation(), Sound.ITEM_CROP_PLANT, 1f, 1f);
        }
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/9bb9f7258408a90bc580d3e04c2367372e06123a4c0f54b79de765a8574b7c93")
                .setDisplayName(StringUtils.colorize("&b&l" + getName()))
                .setLore("", StringUtils.colorize("&7Automatically replants the same crop you harvested!"));
        addDefaultLore(builder, hoe);
        builder.addLore("", StringUtils.colorize("&cYou must have the seed in your inventory for this to work!"));
        return builder.addLore("").build();
    }
}
