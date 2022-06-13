package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.sakurabranch.utils.CropUtils;
import io.github.itstaylz.sakurabranch.utils.PlayerUtils;
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

public class AutoPickupUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoPickupUpgrade() {
        super("upgrade_autopickup", "AutoPickup", 1000, 1, 1, BlockDropItemEvent.class);
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
                    if (PlayerUtils.canPickup(player, stack)) {
                        player.getInventory().addItem(stack);
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                        toRemove.add(item);
                    }
                }
                event.getItems().removeAll(toRemove);
                if (!event.getItems().isEmpty())
                    player.sendMessage("> Inventory full!");
            }
        }
    }

    @Override
    public ItemStack getMenuSkull() {
        return null;
    }
}
