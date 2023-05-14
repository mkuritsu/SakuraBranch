package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.utils.PlayerUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraBranch extends JavaPlugin implements Listener {

    private static Economy economy;
    private static SakuraConfig config;
    private static MessagesConfig messagesConfig;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().severe("Failed to setup economy! Check if you have vault and a compatible economy plugin.");
            return;
        }
        messagesConfig = new MessagesConfig(this);
        config = new SakuraConfig(this);
        Bukkit.getPluginManager().registerEvents(new HoeListener(), this);
        getCommand("sakurabranch").setExecutor(new SakuraBranchCommand());
        getCommand("sakurabranchreload").setExecutor(new SakuraBranchCommand());
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

    public static MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static SakuraConfig getPluginConfig() {
        return config;
    }

    private final class SakuraBranchCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.getInventory().addItem(HoeManager.createNewHoe());
                } else {
                    sender.sendMessage(StringUtils.colorize("&cUse /" + label + " <player name>"));
                }
            } else {
                String playerName = args[0];
                Player target = Bukkit.getPlayerExact(playerName);
                if (target == null) {
                    sender.sendMessage(StringUtils.colorize(getMessagesConfig().getMessage("player_not_found")));
                } else {
                    ItemStack hoe = HoeManager.createNewHoe();
                    if (PlayerUtils.canPickup(target, hoe))
                        target.getInventory().addItem(hoe);
                    else
                        target.getWorld().dropItemNaturally(target.getLocation(), hoe);
                }
            }
            return true;
        }
    }

    private final class ReloadCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (command.getName().equalsIgnoreCase("sakurabranchreload")) {
                SakuraBranch.getMessagesConfig().reload();
                sender.sendMessage(StringUtils.colorize("&aSakuraBranch has been reloaed!"));
                return true;
            }
            return false;
        }
    }

}
