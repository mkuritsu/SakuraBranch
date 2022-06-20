package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrade;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class HoeManager {

    private static final JavaPlugin PLUGIN;
    private static final ItemStack HOE_ITEM;
    private static final NamespacedKey CROPS_KEY;

    static {
        PLUGIN = JavaPlugin.getPlugin(SakuraBranch.class);
        CROPS_KEY = new NamespacedKey(PLUGIN, "sakurabranch_crops");
        HOE_ITEM = new ItemBuilder(new ItemStack(Material.NETHERITE_HOE))
                .setDisplayName(StringUtils.colorize("&d❀ &d&lSakura &b&lBranch &d❀"))
                .addEnchant(Enchantment.DURABILITY, 1)
                .setUnbreakable(true)
                .addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
        refreshLore(HOE_ITEM);
        ItemMeta meta = HOE_ITEM.getItemMeta();
        meta.getPersistentDataContainer().set(CROPS_KEY, PersistentDataType.LONG, 0L);
        HOE_ITEM.setItemMeta(meta);
    }

    public static ItemStack createNewHoe() {
        return HOE_ITEM.clone();
    }

    public static boolean isSakuraBenchHoe(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null)
            return meta.getPersistentDataContainer().has(CROPS_KEY, PersistentDataType.LONG);
        return false;
    }

    public static boolean hasUpgrade(ItemStack item, HoeUpgrade<?> upgrade) {
        return getUpgradeLevel(item, upgrade) > 0;
    }

    public static int getUpgradeLevel(ItemStack item, HoeUpgrade<?> upgrade) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return 0;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PLUGIN, upgrade.getKey());
        Integer level = container.get(key, PersistentDataType.INTEGER);
        return level != null ? level : 0;
    }

    public static void addUpgrade(ItemStack item, HoeUpgrade<?> upgrade, int level) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PLUGIN, upgrade.getKey());
        container.set(key, PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);
        refreshLore(item);
    }

    public static void removeUpgrade(ItemStack item, HoeUpgrade<?> upgrade) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PLUGIN, upgrade.getKey());
        container.remove(key);
        item.setItemMeta(meta);
        refreshLore(item);
    }

    private static void refreshLore(ItemStack hoe) {
        List<String> lore = new ArrayList<>();
        lore.add(StringUtils.colorize("&7A sacred branch from the &dSakura &7tree."));
        lore.add(StringUtils.colorize("&7This will allow you to become a master farmer!"));
        lore.add("");
        int count = 0;
        for (HoeUpgrade<?> upgrade : HoeUpgrades.getAllUpgrades()) {
            if (hasUpgrade(hoe, upgrade)) {
                count++;
                lore.add(StringUtils.colorize("&d" + upgrade.getName() + " &b" + HoeManager.getUpgradeLevel(hoe, upgrade)));
            }
        }
        if (count > 0)
            lore.add("");
        lore.add(StringUtils.colorize("&7To access upgrade menu: &dRight Click"));
        lore.add(" ");
        lore.add(StringUtils.colorize("&dCrops Harvested: &b" + getHarvestedCrops(hoe)));
        lore.add(" ");
        ItemMeta meta = hoe.getItemMeta();
        meta.setLore(lore);
        hoe.setItemMeta(meta);
    }

    public static void buyUpgrade(ItemStack hoe, Player player, HoeUpgrade<?> upgrade) {
        double balance = SakuraBranch.getEconomy().getBalance(player);
        int currentLevel = getUpgradeLevel(hoe, upgrade);
        double price = upgrade.getPrice(currentLevel + 1);
        if (balance >= price) {
            SakuraBranch.getEconomy().withdrawPlayer(player, price);
            addUpgrade(hoe, upgrade, currentLevel + 1);
            player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &dYou have bought &b" + upgrade.getName()));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f);
        } else {
            player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &cYou don't have enough money to buy this upgrade!"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT , 1f, 0.5f);
        }
    }

    public static void refundUpgrade(ItemStack hoe, Player player, HoeUpgrade<?> upgrade) {
        int currentLevel = getUpgradeLevel(hoe, upgrade);
        double refundAmount = 0;
        for (int i = 1; i <= currentLevel; i++) {
            refundAmount += upgrade.getPrice(i);
        }
        SakuraBranch.getEconomy().depositPlayer(player, refundAmount);
        removeUpgrade(hoe, upgrade);
        player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &dYou have refunded &b" + upgrade.getName()));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f);
    }

    public static boolean canBuy(ItemStack hoe, HoeUpgrade<?> upgrade) {
        if (HoeManager.getUpgradeLevel(hoe, upgrade) < upgrade.getMaxLevel()) {
            for (HoeUpgrade<?> other : upgrade.getIncompatibleUpgrades()) {
                if (HoeManager.hasUpgrade(hoe, other))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static boolean canRefund(ItemStack hoe, HoeUpgrade<?> upgrade) {
        return hasUpgrade(hoe, upgrade);
    }

    public static long getHarvestedCrops(ItemStack hoe) {
        ItemMeta meta = hoe.getItemMeta();
        if (meta == null)
            return 0;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Long value = container.get(CROPS_KEY, PersistentDataType.LONG);
        return value != null ? value : 0;
    }

    public static void increaseHarvestedCrops(ItemStack hoe) {
        ItemMeta meta = hoe.getItemMeta();
        if (meta == null)
            return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        long current = getHarvestedCrops(hoe);
        long newValue = current == Long.MAX_VALUE ? Long.MAX_VALUE : current + 1L;
        container.set(CROPS_KEY, PersistentDataType.LONG, newValue);
        hoe.setItemMeta(meta);
        refreshLore(hoe);
    }
}
