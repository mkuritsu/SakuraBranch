package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.Material;

public final class CropUtils {

    private static final Material[] CROPS = { Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOT, Material.NETHER_WART };

    public static boolean isCrop(Material material) {
        for (Material crop : CROPS) {
            if (material == crop)
                return true;
        }
        return false;
    }
}
