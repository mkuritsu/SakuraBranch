package io.github.itstaylz.sakurabranch.upgrades;

import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class HoeUpgrades {

    private static final HashMap<Class<? extends Event>, Set<HoeUpgrade<?>>> UPGRADES_MAP = new HashMap<>();

    public static final HoeUpgrade<?> AUTO_PICKUP = new AutoPickupUpgrade();

    public static final HoeUpgrade<?> AUTO_PLANT = new AutoPlantUpgrade();

    public static final HoeUpgrade<?> AUTO_SELL = new AutoSellUpgrade();

    public static final HoeUpgrade<?> MULTIPLIER = new MultiplierUpgrade();

    static {
        registerUpgrade(AUTO_PICKUP);
        registerUpgrade(AUTO_PLANT);
        registerUpgrade(AUTO_SELL);
        registerUpgrade(MULTIPLIER);
    }

    private static void registerUpgrade(HoeUpgrade<?> upgrade) {
        Set<HoeUpgrade<?>> upgrades = UPGRADES_MAP.get(upgrade.getEventClass());
        if (upgrades == null) {
            upgrades = new HashSet<>();
            UPGRADES_MAP.put(upgrade.getEventClass(), upgrades);
        }
        upgrades.add(upgrade);
    }

    public static Set<HoeUpgrade<?>> getUpgrades(Class<? extends Event> eventClass) {
        return UPGRADES_MAP.get(eventClass);
    }

    public static Set<HoeUpgrade<?>> getAllUpgrades() {
        Set<HoeUpgrade<?>> upgrades = new HashSet<>();
        for (Set<HoeUpgrade<?>> set : UPGRADES_MAP.values()) {
            upgrades.addAll(set);
        }
        return upgrades;
    }
}
