package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.PlayerUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import io.github.itstaylz.sakurabranch.utils.*;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutoPickupUpgrade extends HoeUpgrade<BlockDropItemEvent> {

    public AutoPickupUpgrade() {
        super("sakurabranch_upgrade_autopickup", "Auto Pickup", Arrays.asList(10000), BlockDropItemEvent.class);
    }

    @Override
    public void onTrigger(BlockDropItemEvent event, Player player, ItemStack hoe, int upgradeLevel) {
        BlockState state = event.getBlockState();
        if (!CropUtils.isCrop(state.getType()))
            return;
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
            player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &b&l" + getName() + "&7> &c&lWarning! &7Inventory full!"));
    }

    @Override
    public ItemStack getMenuItem(ItemStack hoe) {
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        SkullBuilder builder = new SkullBuilder()
                .setSkinFromURL("https://textures.minecraft.net/texture/82090aa835f6b97eea8dad4309e96e6c85e727749a24fb7362af79c4d57f3e89")
                .setDisplayName(StringUtils.colorize("&b&l" + getName()))
                .setLore("", StringUtils.colorize("&7Automatically picks up harvested crops!"));
        if (currentLevel == getMaxLevel()) {
            builder.addLore(StringUtils.colorize("&a&lMAX LEVEL REACHED!"));
            builder.addLore(StringUtils.colorize("&7Right-Click to &cRefund"));
        } else if (HoeManager.hasUpgrade(hoe, HoeUpgrades.AUTO_SELL)) {
            builder.addLore(StringUtils.colorize("&c&lINCOMPATIBLE WITH: &f" + HoeUpgrades.AUTO_SELL.getName()));
        } else {
            builder.addLore(StringUtils.colorize("&7Price: &a$" + getPrice(currentLevel+1)));
            builder.addLore(StringUtils.colorize("&7Left-Click to &aBuy"));
        }
        return builder.addLore("").build();
    }

    @Override
    public List<HoeUpgrade<?>> getIncompatibleUpgrades() {
        return List.of(HoeUpgrades.AUTO_SELL);
    }
}
