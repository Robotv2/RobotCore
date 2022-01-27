package fr.robotv2.robotcore.api;

import fr.robotv2.robotcore.RobotCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultAPI {
    private static Economy eco;
    public static boolean initialize() {
        if (RobotCore.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = RobotCore.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return true;
    }

    public static boolean hasEnough(OfflinePlayer offlinePlayer, Double value) {
        return eco.has(offlinePlayer, value);
    }

    public static Double getBalance(OfflinePlayer offlinePlayer) {
        return eco.getBalance(offlinePlayer);
    }

    public static void setBalance(OfflinePlayer offlinePlayer, Double value) {
        VaultAPI.giveMoney(offlinePlayer, value - VaultAPI.getBalance(offlinePlayer));
    }

    public static void giveMoney(OfflinePlayer offlinePlayer, Double value) {
        eco.depositPlayer(offlinePlayer, value);
    }

    public static void takeMoney(OfflinePlayer offlinePlayer, Double value) {
        eco.withdrawPlayer(offlinePlayer, value);
    }
}
