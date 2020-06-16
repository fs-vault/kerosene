package xyz.nkomarn.Kerosene;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.data.LocalStorage;
import xyz.nkomarn.Kerosene.data.PlayerData;
import xyz.nkomarn.Kerosene.data.Redis;
import xyz.nkomarn.Kerosene.listeners.PlayerQuitListener;
import xyz.nkomarn.Kerosene.menu.MenuHandler;
import xyz.nkomarn.Kerosene.util.EconomyUtil;

public class Kerosene extends JavaPlugin {
    private static Kerosene kerosene;
    private static Essentials essentials;

    @Override
    public void onEnable() {
        kerosene = this;
        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("essentials");

        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {
            getLogger().severe("Failed to connect to database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!Redis.connect(getConfig().getString("redis.host"),
                getConfig().getInt("redis.port"),
                getConfig().getString("redis.password"),
                getConfig().getInt("redis.pool-size"),
                getConfig().getInt("redis.timeout"))) {
            getLogger().severe("Failed to connect to Redis.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!LocalStorage.initialize()) {
            getLogger().severe("Failed to initialize local storage.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            LocalStorage.createTables();
        }

        if (!EconomyUtil.initializeEconomy()) {
            getLogger().severe("Failed to initialize the economy.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Bukkit.getPluginManager().registerEvents(new MenuHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        MenuHandler.closeAll();
        PlayerData.close();
        Redis.close();
    }

    /**
     * Fetches an instance of the Kerosene plugin.
     * @return Kerosene plugin instance.
     */
    public static Kerosene getKerosene() {
        return kerosene;
    }

    /**
     * Fetches an instance of the Essentials plugin.
     * @return Essentials plugin instance.
     */
    public static Essentials getEssentials() {
        return essentials;
    }
}
