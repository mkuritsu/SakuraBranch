package io.github.itstaylz.sakurabranch.utils;

import io.github.itstaylz.sakurabranch.SakuraBranch;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;


public final class CropUtils {

    public static boolean isCrop(Material material) {
        return SakuraBranch.getPluginConfig().getXp(material) != -1;
    }

    public static void giveCropXP(Player player, BlockState cropState, double multiplier) {
        Material blockType = cropState.getType();
        if (cropState.getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()) {
            int xp = SakuraBranch.getPluginConfig().getXp(blockType);
            if (xp != -1) {
                xp *= multiplier;
                player.giveExp(xp);
            }
        }
    }
}
