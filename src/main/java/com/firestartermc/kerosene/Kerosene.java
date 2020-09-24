package com.firestartermc.kerosene;

import com.earth2me.essentials.Essentials;
import com.firestartermc.kerosene.data.db.PlayerData;
import com.firestartermc.kerosene.data.redis.Redis;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.GuiListener;
import com.firestartermc.kerosene.user.User;
import com.firestartermc.kerosene.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.firestartermc.kerosene.commands.KeroseneCommand;
import com.firestartermc.kerosene.listener.player.QuitListener;
import com.firestartermc.kerosene.util.internal.Debug;
import com.firestartermc.kerosene.util.Economy;
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

    private static Kerosene KEROSENE;
    private static ForkJoinPool POOL;
    private static Redis REDIS;

    private final UserManager userManager;

    private Essentials essentials;

    public Kerosene() {
        this.userManager = new UserManager(this);
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        getCommand("kerosene").setExecutor(new KeroseneCommand(this));
        saveDefaultConfig();

        KEROSENE = this;
        POOL = new ForkJoinPool(getConfig().getInt("pool.threads"));
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

    public static Kerosene getKerosene() {
        return KEROSENE;
    }

    public static ForkJoinPool getPool() {
        return POOL;
    }

    public static Redis getRedis() {
        return REDIS;
    }


    @NotNull
    public UserManager getUserManager() {
        return userManager;
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

        if (getServer().getPluginManager().isPluginEnabled("Vault") && !Economy.initializeEconomy()) {
            Bukkit.shutdown();
            throw new RuntimeException("Failed to initialize the economy.");
        }
    }
}
