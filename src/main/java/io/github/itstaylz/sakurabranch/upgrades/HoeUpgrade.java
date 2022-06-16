package io.github.itstaylz.sakurabranch.upgrades;

import io.github.itstaylz.hexlib.items.SkullBuilder;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakurabranch.HoeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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

    protected void addDefaultLore(SkullBuilder builder, ItemStack hoe) {
        int currentLevel = HoeManager.getUpgradeLevel(hoe, this);
        if (currentLevel == getMaxLevel()) {
            builder.addLore(StringUtils.colorize("&a&lMAX LEVEL REACHED!"));
        } else {
            Set<String> incompatibles = new HashSet<>();
            for (HoeUpgrade<?> upgrade : getIncompatibleUpgrades()) {
                if (HoeManager.hasUpgrade(hoe, upgrade))
                    incompatibles.add(upgrade.getName());
            }
            if (incompatibles.isEmpty()) {
                String price = formatNumber(getPrice(currentLevel + 1));
                builder.addLore(StringUtils.colorize("&7Price: &a$" + price));
                builder.addLore(StringUtils.colorize("&7Left-Click to &aBuy"));
            } else {
                String incompatibleString = StringUtils.appendArray(incompatibles.toArray(new String[0]), ",");
                builder.addLore(StringUtils.colorize("&c&lINCOMPATIBLE WITH: &f" + incompatibleString));
            }
        }
        if (currentLevel > 0)
            builder.addLore(StringUtils.colorize("&7Right-Click to &cRefund"));
    }

    private String formatNumber(int number) {
        Deque<String> deque = new ArrayDeque<>();
        int counter = 0;
        while (number > 0) {
            if (counter == 3) {
                deque.addFirst(",");
                counter = 0;
            }
            deque.addFirst((number % 10) + "");
            number /= 10;
            counter++;
        }
        StringBuilder sb = new StringBuilder();
        while(!deque.isEmpty())
            sb.append(deque.pop());
        return sb.toString();
    }
}
