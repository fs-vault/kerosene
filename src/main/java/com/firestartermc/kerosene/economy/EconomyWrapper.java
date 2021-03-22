package com.firestartermc.kerosene.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Vault API {@link Economy} method wrapper.
 * <p>
 * This class provides methods for common economy actions that rely
 * on the Vault API. The intention is that external plugins that
 * depend on Kerosene will be able to quickly and easily interface
 * with the current server's economy without having to externally
 * import of depend on the Vault API.
 *
 * @author Firestarter Minecraft Servers
 * @see Economy
 * @since 5.0
 */
@ThreadSafe
public class EconomyWrapper {

    private final Economy economy;

    public EconomyWrapper(@NotNull Economy economy) {
        this.economy = economy;
    }

    public String getName() {
        return economy.getName();
    }

    public int fractionalDigits() {
        return economy.fractionalDigits();
    }

    public String format(double amount) {
        return economy.format(amount);
    }

    public String currencyNamePlural() {
        return economy.currencyNamePlural();
    }

    public String currencyNameSingular() {
        return economy.currencyNameSingular();
    }

    public boolean hasAccount(String playerName) {
        return economy.hasAccount(playerName);
    }

    public boolean hasAccount(OfflinePlayer player) {
        return economy.hasAccount(player);
    }

    public boolean hasAccount(String playerName, String worldName) {
        return economy.hasAccount(playerName, worldName);
    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return economy.hasAccount(player, worldName);
    }

    public double getBalance(String playerName) {
        return economy.getBalance(playerName);
    }

    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    public double getBalance(String playerName, String world) {
        return economy.getBalance(playerName, world);
    }

    public double getBalance(OfflinePlayer player, String world) {
        return economy.getBalance(player, world);
    }

    public boolean has(String playerName, double amount) {
        return economy.has(playerName, amount);
    }

    public boolean has(OfflinePlayer player, double amount) {
        return economy.has(player, amount);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return economy.has(playerName, worldName, amount);
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return economy.has(player, worldName, amount);
    }

    public boolean withdrawPlayer(String playerName, double amount) {
        return economy.withdrawPlayer(playerName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public boolean withdrawPlayer(OfflinePlayer player, double amount) {
        return economy.withdrawPlayer(player, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public boolean withdrawPlayer(String playerName, String worldName, double amount) {
        return economy.withdrawPlayer(playerName, worldName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public boolean withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return economy.withdrawPlayer(player, worldName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public boolean depositPlayer(String playerName, double amount) {
        return economy.depositPlayer(playerName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public boolean depositPlayer(OfflinePlayer player, double amount) {
        return economy.depositPlayer(player, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }


    public boolean depositPlayer(String playerName, String worldName, double amount) {
        return economy.depositPlayer(playerName, worldName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }


    public boolean depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return economy.depositPlayer(player, worldName, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }
}
