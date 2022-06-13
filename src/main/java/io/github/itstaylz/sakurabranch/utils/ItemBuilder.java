package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        List<String> lore = this.meta.getLore() != null ? this.meta.getLore() : new ArrayList<>();
        lore.addAll(Arrays.asList(lines));
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        this.meta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }


}
