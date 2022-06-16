package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.storage.files.YamlFile;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SakuraConfig {

    private final YamlFile configFile;

    public SakuraConfig(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.configFile = new YamlFile(new File(plugin.getDataFolder(), "config.yml"));
    }

    public void reload() {
        this.configFile.reloadConfig();
    }

    public List<Integer> getUpgradePrices(String upgradeKey) {
        String path = "upgrades." + upgradeKey;
        if (configFile.contains(path)) {
            return this.configFile.getConfig().getIntegerList(path);
        }
        return new ArrayList<>();
    }

    public int getSellPrice(Material material) {
        String path = "prices." + material.name();
        if (configFile.contains(path)) {
            return configFile.get(path, Integer.class);
        }
        return -1;
    }

    public int getXp(Material material) {
        String path = "xp." + material.name();
        if (configFile.contains(path)) {
            return configFile.get(path, Integer.class);
        }
        return -1;
    }

    public Material getSeeds(Material material) {
        String path = "seeds." + material.name();
        if (configFile.contains(path))
            return Material.valueOf(configFile.get(path, String.class));
        return Material.AIR;
    }

    public double getMultiplierAmount(int level) {
        String path = "multiplier_rates";
        if (this.configFile.contains(path))
            return this.configFile.getConfig().getDoubleList(path).get(level - 1);
        return 0;
    }
}
