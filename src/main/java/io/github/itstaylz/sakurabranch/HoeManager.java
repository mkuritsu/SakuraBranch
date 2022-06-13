package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.sakurabranch.gui.Menu;
import io.github.itstaylz.sakurabranch.gui.MenuItem;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrade;
import io.github.itstaylz.sakurabranch.utils.ItemBuilder;
import io.github.itstaylz.sakurabranch.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class HoeManager {

    private static final JavaPlugin PLUGIN;
    private static final ItemStack HOE_ITEM;
    public static final NamespacedKey HOE_KEY;

    static {
        PLUGIN = JavaPlugin.getPlugin(SakuraBranch.class);
        HOE_KEY = new NamespacedKey(PLUGIN, "item_sakurabench");
        HOE_ITEM = new ItemBuilder(new ItemStack(Material.NETHERITE_HOE))
                .setDisplayName(StringUtils.colorize("&d🌸 &d&lSakura&b&lBranch &d🌸"))
                .setLore(
                        StringUtils.colorize("&7A sacred branch from the &dSakura &7tree."),
                        StringUtils.colorize("&7This will allow you to become a master farmer!"),
                        "",
                        StringUtils.colorize("&7To access upgrade menu: &dRight Click")
                        )
                .addEnchant(Enchantment.DURABILITY, 1)
                .setUnbreakable(true)
                .addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES)
                .build();
        ItemMeta meta = HOE_ITEM.getItemMeta();
        meta.getPersistentDataContainer().set(HOE_KEY, PersistentDataType.BYTE, (byte) 1);
        HOE_ITEM.setItemMeta(meta);
    }

    public static ItemStack createNewHoe() {
        return HOE_ITEM.clone();
    }

    public static boolean isSakuraBenchHoe(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return meta.getPersistentDataContainer().has(HOE_KEY, PersistentDataType.BYTE);
        }
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
    }

    public static void removeUpgrade(ItemStack item, HoeUpgrade<?> upgrade) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PLUGIN, upgrade.getKey());
        container.remove(key);
        item.setItemMeta(meta);
    }

    public static void openUpgradeMenu(Player player, ItemStack hoe) {
        Menu menu = new Menu(5*9, StringUtils.colorize("&d🌸 &dBranch &bUpgrader &d🌸"));
        for (int i = 0; i < 9; i++) {
        }
        menu.open(player);
    }
}
