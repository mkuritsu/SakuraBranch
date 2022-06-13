package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.sakurabranch.utils.CropUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class AutoPlantUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoPlantUpgrade() {
        super("upgrade_autoplant", "AutoPlant", 100, 1, 1, BlockDropItemEvent.class);
    }

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
    public ItemStack getMenuSkull() {
        return null;
    }
}
