package com.firestartermc.kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Utility class for interfacing with a Vault API economy instance.
 */
public final class Economy {
    private static net.milkbowl.vault.economy.Economy economy;

    private Economy() {
    }

    /**
     * Fetches an instance of the Vault economy.
     * @return Vault economy instance.
     */
    public static net.milkbowl.vault.economy.Economy getEconomy() {
        return economy;
    }

    /**
     * Initializes the Vault economy.
     * @return Whether the economy has been successfully initialized.
     */
    public static boolean initializeEconomy() {
        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> provider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (provider == null) return false;
        economy = provider.getProvider();
        return true;
    }

    /**
     * Returns the player's current account balance.
     * @param player The player to check balance for.
     * @return The player's current account balance.
     */
    public static double getBalance(final OfflinePlayer player) {
        double balance = 0.0;
        try {
            balance = getEconomy().getBalance(player);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Deposits an amount into a player's balance.
     * @param player The player to deposit money to.
     * @param amount Amount of money to deposit.
     */
    public static void deposit(final OfflinePlayer player, final double amount) {
        getEconomy().depositPlayer(player, amount);
    }

    /**
     * Withdraws an amount from a player's balance.
     * @param player The player to withdraw money from.
     * @param amount Amount of money to withdraw.
     */
    public static void withdraw(final OfflinePlayer player, final double amount) {
        getEconomy().withdrawPlayer(player, amount);
    }
}
