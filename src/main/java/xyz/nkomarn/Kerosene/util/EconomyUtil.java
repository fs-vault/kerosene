package xyz.nkomarn.Kerosene.util;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Utility class for interfacing with a
 * Vault API economy instance.
 */
public class EconomyUtil {
    private static Economy economy;

    /**
     * Fetches an instance of the Vault economy.
     * @return Vault economy instance.
     */
    public static Economy getEconomy() {
        return economy;
    }

    /**
     * Initializes the Vault economy.
     * @return Whether the economy has been successfully initialized.
     */
    public static boolean initializeEconomy() {
        RegisteredServiceProvider<Economy> provider = Bukkit.getServer()
                .getServicesManager().getRegistration(Economy.class);
        if (provider == null) return false;
        economy = provider.getProvider();
        return true;
    }
}
