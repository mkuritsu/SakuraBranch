package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.PlayerUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.SakuraBranch;
import io.github.itstaylz.sakurabranch.utils.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutoSellUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoSellUpgrade() {
        super("sakurabranch_upgrade_autosell", "Auto Sell", SakuraBranch.getPluginConfig().getUpgradePrices("auto_sell"), BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        BlockState state = event.getBlockState();
        if (!CropUtils.isCrop(state.getType()))
            return;
        Set<Item> toRemove = new HashSet<>();
        for (Item item : event.getItems()) {
            ItemStack stack = item.getItemStack();
            int singlePrice = SakuraBranch.getPluginConfig().getSellPrice(stack.getType());
            if (singlePrice != -1) {
                int totalPrice = singlePrice * stack.getAmount();
                SakuraBranch.getEconomy().depositPlayer(player, totalPrice);
                PlayerUtils.sendActionbarMessage(player, StringUtils.colorize("&a+" + totalPrice + "$"));
                toRemove.add(item);
            }
        }
        event.getItems().removeAll(toRemove);
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/a953388ccd6ff64fc1950dd4cec4adeaa348aaabcc532828e7144f509f23c")
                .setDisplayName(StringUtils.colorize("&d&l" + getName()))
                .setLore("", StringUtils.colorize("&7Automatically sells what you harvest!"));
        addDefaultLore(builder, hoe);
        return builder.addLore("").build();
    }

    @Override
    public List<HoeUpgrade<?>> getIncompatibleUpgrades() {
        return List.of(HoeUpgrades.AUTO_PICKUP);
    }
}
