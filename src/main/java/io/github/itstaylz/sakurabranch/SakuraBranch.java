package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraBranch extends JavaPlugin implements Listener {

    private static Economy economy;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().severe("Failed to setup economy! Check if you have vault and a compatible economy plugin.");
            return;
        }
        Bukkit.getPluginManager().registerEvents(new HoeListener(), this);
        getCommand("sakurabranch").setExecutor(new SakuraBranchCommand());
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null)
            return false;
        economy = provider.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return economy;
    }

    private static final class SakuraBranchCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player player) {
                player.getInventory().addItem(HoeManager.createNewHoe());
            } else {
                sender.sendMessage(StringUtils.colorize("&CThis command can only be used by players!"));
            }
            return true;
        }
    }

}
