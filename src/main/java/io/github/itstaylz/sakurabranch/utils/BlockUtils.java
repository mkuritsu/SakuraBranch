package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.block.Block;

public final class BlockUtils {

    public static Block getBlockBellow(Block block) {
        return block.getWorld().getBlockAt(block.getLocation().subtract(0, 1, 0));
    }
}
