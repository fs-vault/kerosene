package com.firestartermc.kerosene;

import com.earth2me.essentials.Essentials;
import com.firestartermc.kerosene.data.db.LocalStorage;
import com.firestartermc.kerosene.data.db.PlayerData;
import com.firestartermc.kerosene.data.redis.Redis;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.GuiListener;
import com.firestartermc.kerosene.user.User;
import com.firestartermc.kerosene.user.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.firestartermc.kerosene.commands.KeroseneCommand;
import com.firestartermc.kerosene.listener.player.QuitListener;
import com.firestartermc.kerosene.util.internal.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * Kerosene is a core library that many of Firestarter's plugins extend. This library is intended to be
 * universal, and is able to be used on any server, regardless of gamemode.
 */
public class Kerosene extends JavaPlugin {

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6&lKerosene: &7");
    public static final String DEBUG_CATEGORY_GUI_INTERACT = "gui:interact";

    private static Kerosene kerosene;

    private static Redis REDIS;

    private final ForkJoinPool threadPool;
    private final UserManager userManager;

    private Economy economy;
    private Essentials essentials;

    public Kerosene() {
        kerosene = this;
        this.threadPool = new ForkJoinPool(3);
        this.userManager = new UserManager(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        getCommand("kerosene").setExecutor(new KeroseneCommand(this));

        // REDIS = new Redis(getConfig().getString("redis.uri", ""));

        Arrays.asList(
                userManager,
                new GuiListener(),
                new QuitListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, this));

        registerHooks();


        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {

            Bukkit.shutdown();
            throw new RuntimeException("Failed to connect to database.");
        }

        Debug.registerCategory(DEBUG_CATEGORY_GUI_INTERACT);
    }

    @Override
    public void onDisable() {
        Gui.closeAll();
        PlayerData.close();
        REDIS.shutdown();
    }

    @NotNull
    public static Kerosene getKerosene() {
        return kerosene;
    }

    @NotNull
    public ForkJoinPool getPool() {
        return threadPool;
    }

    @NotNull
    public UserManager getUserManager() {
        return userManager;
    }

    @NotNull
    public LocalStorage getLocalStorage(String name) {
        return new LocalStorage(getDataFolder(), name);
    }

    @Nullable
    public static Redis getRedis() {
        return REDIS;
    }

    @Nullable
    public Economy getEconomy() {
        return economy;
    }

    @Nullable
    public Essentials getEssentials() {
        return essentials;
    }

    private void registerHooks() {
        if (getServer().getPluginManager().isPluginEnabled("Essentials")) {
            essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
            getLogger().info("Hooked into Essentials.");
        }

        if (getServer().getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Economy> provider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

            if (provider == null) {
                getLogger().warning("Failed to hook into Vault.");
                return;
            }

            economy = provider.getProvider();
        }
    }
}
