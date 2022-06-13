package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.sakurabranch.gui.MenuListener;
import io.github.itstaylz.sakurabranch.upgrades.HoeUpgrades;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraBranch extends JavaPlugin implements Listener {

    public static Economy ECONOMY;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new HoeListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        if (!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().severe("Failed to setup economy! Check if you have vault and a compatible economy plugin.");
            return;
        }
        // DEBUG
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null)
            return false;
        ECONOMY = provider.getProvider();
        return true;
    }


    // DEBUG
    @EventHandler
    private void onEgg(PlayerEggThrowEvent event) {
        Player player = event.getPlayer();
        ItemStack item = HoeManager.createNewHoe();
        //HoeManager.addUpgrade(item, HoeUpgrades.AUTO_PICKUP, 1);
        HoeManager.addUpgrade(item, HoeUpgrades.AUTO_PLANT, 1);
        HoeManager.addUpgrade(item, HoeUpgrades.AUTO_SELL, 1);
        player.getInventory().addItem(item);
    }
}
