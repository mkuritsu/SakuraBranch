package io.github.itstaylz.sakurabranch.upgrades;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class HoeUpgrade<E extends Event> {

    private final String key, name;
    private final List<Integer> prices;
    private final Class<E> eventClass;

    public HoeUpgrade(String key, String name, List<Integer> prices, Class<E> eventClass) {
        this.key = key;
        this.name = name;
        this.prices = prices;
        this.eventClass = eventClass;
    }

    public abstract void onTrigger(E event, Player player, ItemStack hoe, int upgradeLevel);

    public int getPrice(int level) {
        return this.prices.get(level - 1);
    }

    public String getKey() {
        return key;
    }

    public int getMaxLevel() {
        return this.prices.size();
    }

    public String getName() {
        return name;
    }

    public abstract ItemStack getMenuItem(ItemStack hoe);

    public Class<E> getEventClass() {
        return eventClass;
    }

    public List<HoeUpgrade<?>> getIncompatibleUpgrades() {
        return new ArrayList<>();
    }
}
