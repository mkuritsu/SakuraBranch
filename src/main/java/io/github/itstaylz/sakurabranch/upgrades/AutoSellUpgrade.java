package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.PlayerUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import io.github.itstaylz.sakurabranch.SakuraBranch;
import io.github.itstaylz.sakurabranch.utils.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AutoSellUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoSellUpgrade() {
        super("sakurabranch_upgrade_autosell", "Auto Sell", Arrays.asList(25000), BlockDropItemEvent.class);
    }

    // TODO get item prices from config
    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        BlockState state = event.getBlockState();
        if (!CropUtils.isCrop(state.getType()))
            return;
        BlockData data = state.getBlockData();
        if (data instanceof Ageable ageable) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                for (Item item : event.getItems()) {
                    ItemStack stack = item.getItemStack();
                    int price = stack.getAmount() * 100;
                    SakuraBranch.getEconomy().depositPlayer(player, price);
                    PlayerUtils.sendActionbarMessage(player, StringUtils.colorize("&a+ " + price + "$"));
                }
                event.getItems().clear();
            }
        }
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/a953388ccd6ff64fc1950dd4cec4adeaa348aaabcc532828e7144f509f23c")
                .setDisplayName(StringUtils.colorize("&d&l" + getName()))
                .setLore("", StringUtils.colorize("&7Automatically sells what you harvest!"));
        if (currentLevel == getMaxLevel()) {
            builder.addLore(StringUtils.colorize("&a&lMAX LEVEL REACHED!"));
            builder.addLore(StringUtils.colorize("&7Right-Click to &cRefund"));
        } else if (HoeManager.hasUpgrade(hoe, HoeUpgrades.AUTO_PICKUP)) {
            builder.addLore(StringUtils.colorize("&c&lINCOMPATIBLE WITH: &f" + HoeUpgrades.AUTO_PICKUP.getName()));
        } else {
            builder.addLore(StringUtils.colorize("&7Price: &a$" + getPrice(currentLevel+1)));
            builder.addLore(StringUtils.colorize("&7Left-Click to &aBuy"));
        }
        return builder.addLore("").build();
    }

    @Override
    public List<HoeUpgrade<?>> getIncompatibleUpgrades() {
        return List.of(HoeUpgrades.AUTO_PICKUP);
    }
}
