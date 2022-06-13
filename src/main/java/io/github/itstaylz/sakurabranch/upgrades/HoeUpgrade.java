package io.github.itstaylz.sakurabranch.upgrades;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class HoeUpgrade<E extends Event> {

    private final String key, name;
    private final int basePrice, maxLevel;

    private final float priceMultiplier;
    private final Class<E> eventClass;

    public HoeUpgrade(String key, String name, int basePrice, int maxLevel, float priceMultiplier, Class<E> eventClass) {
        this.key = key;
        this.name = name;
        this.basePrice = basePrice;
        this.maxLevel = maxLevel;
        this.priceMultiplier = priceMultiplier;
        this.eventClass = eventClass;
    }

    public abstract void onTrigger(E event, Player player, ItemStack hoe, int upgradeLevel);

    public int getPrice(int level) {
        int price = this.basePrice;
        for (int i = 1; i < level; i++) {
            price += price * priceMultiplier;
        }
        return price;
    }

    public String getKey() {
        return key;
    }

    public float getPriceMultiplier() {
        return priceMultiplier;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getName() {
        return name;
    }

    public abstract ItemStack getMenuSkull();

    public Class<E> getEventClass() {
        return eventClass;
    }
}
