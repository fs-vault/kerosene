package com.firestartermc.kerosene;

import com.earth2me.essentials.Essentials;
import com.firestartermc.kerosene.data.db.LocalStorage;
import com.firestartermc.kerosene.data.db.RemoteStorage;
import com.firestartermc.kerosene.data.redis.Redis;
import com.firestartermc.kerosene.economy.EconomyWrapper;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.GuiListener;
import com.firestartermc.kerosene.user.User;
import com.firestartermc.kerosene.user.UserManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.firestartermc.kerosene.command.KeroseneCommand;
import com.firestartermc.kerosene.listener.player.QuitListener;
import com.firestartermc.kerosene.util.internal.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Kerosene is a core library that nearly all of Firestarter's
 * in-house plugins, and some external ones, hook into. This library
 * has very miscellaneous utilities and helper classes intended to
 * accelerate development and consolidate version-specific code into
 * a single library.
 * <p>
 * It contains database helper classes, {@link ItemStack} creation and
 * manipulation utilities, an extensive graphical user interface system,
 * and tons of miscellaneous utilities intended to centralize commonly
 * used code fragments.
 * <p>
 * Since 5.0, Kerosene has included a {@link User} system, which defines
 * each {@link Player} on the server as a user and caches data related to
 * that player.
 * <p>
 * Kerosene is intended to be universal, which means it can be used for
 * any given game mode, and does not include specific utilities that would
 * be required by only a single game mode. By doing this, we can delegate
 * game mode specific content to other plugins that depend on Kerosene.
 *
 * @author Firestarter Minecraft Servers
 */
public class Kerosene extends JavaPlugin {

    private static Kerosene kerosene;
    private final UserManager userManager;
    private RemoteStorage playerData;
    private Redis redis;
    private EconomyWrapper economy;
    private Essentials essentials;
    private BukkitAudiences audiences;

    public Kerosene() {
        kerosene = this;
        this.userManager = new UserManager(this);
    }

    public void onEnable() {
        saveDefaultConfig();
        connectDatabases();

        var pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(userManager, this);
        pluginManager.registerEvents(new GuiListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        registerHooks();

        getCommand("kerosene").setExecutor(new KeroseneCommand(this));
        Debug.registerCategory(Debug.DEBUG_CATEGORY_GUI_INTERACT);

        for (var player : getServer().getOnlinePlayers()) {
            userManager.cacheUser(player);
        }
    }

    public void onDisable() {
        Gui.closeAll();

        if (playerData != null) {
            playerData.close();
        }

        if (redis != null) {
            redis.close();
        }

        if (audiences != null) {
            audiences.close();
        }
    }

    @NotNull
    public static Kerosene getKerosene() {
        return kerosene;
    }

    @NotNull
    public UserManager getUserManager() {
        return userManager;
    }

    @Nullable
    public RemoteStorage getPlayerData() {
        return playerData;
    }

    @NotNull
    public LocalStorage getLocalStorage(String name) {
        return new LocalStorage(getDataFolder(), name);
    }

    @Nullable
    public Redis getRedis() {
        return redis;
    }

    @Nullable
    public EconomyWrapper getEconomy() {
        return economy;
    }

    @Nullable
    public Essentials getEssentials() {
        return essentials;
    }

    @NotNull
    public BukkitAudiences getAudiences() {
        return audiences;
    }

    @NotNull
    public Audience getAudience(@NotNull Player player) {
        return audiences.player(player);
    }

    private void connectDatabases() {
        var sqlUrl = getConfig().getString("database.playerdata.url");
        if (sqlUrl != null && sqlUrl.length() > 0) {
            getLogger().info("Connecting to playerdata remote storage.");
            var section = getConfig().getConfigurationSection("database.playerdata");
            playerData = new RemoteStorage(sqlUrl, section.getString("username"), section.getString("password")).connect();
        }

        var redisUri = getConfig().getString("database.redis.uri");
        if (redisUri != null && redisUri.length() > 0) {
            getLogger().info("Connecting to Redis database.");
            redis = new Redis(redisUri);
        }
    }

    private void registerHooks() {
        if (getServer().getPluginManager().isPluginEnabled("Essentials")) {
            essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
            getLogger().info("Hooked into Essentials.");
        }

        if (getServer().getPluginManager().isPluginEnabled("Vault")) {
            var provider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

            if (provider == null) {
                getLogger().warning("Failed to hook into Vault.");
                return;
            }

            economy = new EconomyWrapper(provider.getProvider());
        }

        audiences = BukkitAudiences.create(this);
    }
}
