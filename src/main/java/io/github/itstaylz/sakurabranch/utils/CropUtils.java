package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.Material;

public final class CropUtils {

    private static final Material[] CROPS = { Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOT, Material.NETHER_WART };

    private static final Material[] FARM_BLOCKS = { Material.MELON, Material.PUMPKIN };

    public static boolean isCrop(Material material) {
        for (Material crop : CROPS) {
            if (material == crop)
                return true;
        }
        return false;
    }

    public static boolean isFarmBlock(Material material) {
        for (Material block : FARM_BLOCKS) {
            if (material == block)
                return true;
        }
        return false;
    }

    public static boolean isCropOrFarmBlock(Material material) {
        return isCrop(material) || isFarmBlock(material);
    }
}
