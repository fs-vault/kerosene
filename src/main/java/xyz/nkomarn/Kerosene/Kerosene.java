package xyz.nkomarn.Kerosene;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.data.PlayerData;
import xyz.nkomarn.Kerosene.util.EconomyUtil;

public class Kerosene extends JavaPlugin {
    private static Essentials essentials;

    @Override
    public void onEnable() {
        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {
            getLogger().severe("Failed to connect to database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!EconomyUtil.initializeEconomy()) {
            getLogger().severe("Failed to initialize the economy.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("essentials");
    }

    @Override
    public void onDisable() {
        PlayerData.close();
    }

    /**
     * Fetches an instance of the Essentials plugin.
     * @return Essentials plugin instance.
     */
    public static Essentials getEssentials() {
        return essentials;
    }
}
