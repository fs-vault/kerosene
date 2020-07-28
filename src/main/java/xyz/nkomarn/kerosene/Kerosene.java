package xyz.nkomarn.kerosene;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.kerosene.commands.KeroseneCommand;
import xyz.nkomarn.kerosene.data.db.LocalStorage;
import xyz.nkomarn.kerosene.data.db.PlayerData;
import xyz.nkomarn.kerosene.data.redis.Redis;
import xyz.nkomarn.kerosene.gui.GuiListener;
import xyz.nkomarn.kerosene.listener.player.QuitListener;
import xyz.nkomarn.kerosene.menu.MenuHandler;
import xyz.nkomarn.kerosene.util.internal.Debug;
import xyz.nkomarn.kerosene.util.Economy;

import java.util.concurrent.ForkJoinPool;

/**
 * Kerosene is a core library that many of Firestarter's plugins extend. This library is intended to be
 * universal, and is able to be used on any server, regardless of gamemode.
 */
public class Kerosene extends JavaPlugin {

    public static final String DEBUG_CATEGORY_GUI_INTERACT = "gui:interact";

    private static Kerosene KEROSENE;
    private static Essentials ESSENTIALS;
    private static ForkJoinPool POOL;
    private static LocalStorage STORAGE;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        KEROSENE = this;
        ESSENTIALS = (Essentials) Bukkit.getPluginManager().getPlugin("essentials");
        POOL = new ForkJoinPool(getConfig().getInt("pool.threads"));
        STORAGE = new LocalStorage("kerosene");

        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {

            Bukkit.shutdown();
            throw new RuntimeException("Failed to connect to database.");
        }

        if (!Redis.connect(getConfig().getString("redis.host"),
                getConfig().getInt("redis.port"),
                getConfig().getString("redis.password"),
                getConfig().getInt("redis.pool-size"),
                getConfig().getInt("redis.timeout"))) {

            Bukkit.shutdown();
            throw new RuntimeException("Failed to connect to Redis.");
        }

        if (!Economy.initializeEconomy()) {
            Bukkit.shutdown();
            throw new RuntimeException("Failed to initialize the economy.");
        }

        Debug.registerCategory(DEBUG_CATEGORY_GUI_INTERACT);

        Bukkit.getPluginManager().registerEvents(new MenuHandler(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);

        getCommand("kerosene").setExecutor(new KeroseneCommand());
    }

    @Override
    public void onDisable() {
        MenuHandler.closeAll();
        PlayerData.close();
        Redis.close();
    }

    public static Kerosene getKerosene() {
        return KEROSENE;
    }

    public static ForkJoinPool getPool() {
        return POOL;
    }

    public static Essentials getEssentials() {
        return ESSENTIALS;
    }
}
