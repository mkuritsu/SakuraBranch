package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.sakurabranch.SakuraBranch;
import io.github.itstaylz.sakurabranch.utils.*;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class AutoSellUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoSellUpgrade() {
        super("upgrade_autosell", "AutoSell", 100, 1, 1, BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        BlockState state = event.getBlockState();
        if (!CropUtils.isCropOrFarmBlock(state.getType()))
            return;
        BlockData data = state.getBlockData();
        if (data instanceof Ageable ageable) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                Set<Item> toRemove = new HashSet<>();
                for (Item item : event.getItems()) {
                    ItemStack stack = item.getItemStack();
                    int price = stack.getAmount() * 100;
                    SakuraBranch.ECONOMY.depositPlayer(player, price);
                    player.sendMessage(StringUtils.colorize("&a+ &e" + price + SakuraBranch.ECONOMY.getName()));
                    toRemove.add(item);
                }
                event.getItems().removeAll(toRemove);
            }
        }
    }

    @Override
    public ItemStack getMenuSkull() {
        return new ItemBuilder(SkullUtils.createSkull("14b5f04e-ff63-48f9-9789-fd289175ca44", "https://textures.minecraft.net/texture/a953388ccd6ff64fc1950dd4cec4adeaa348aaabcc532828e7144f509f23c"))
                .setDisplayName(StringUtils.colorize("&d&lAuto Sell"))
                .build();
    }
}
