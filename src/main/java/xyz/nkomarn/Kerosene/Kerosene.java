package xyz.nkomarn.Kerosene;

import com.earth2me.essentials.Essentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.data.PlayerData;

public class Kerosene extends JavaPlugin {
    private static Essentials essentials;
    private static Economy economy;

    @Override
    public void onEnable() {
        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {
            getLogger().severe("Failed to connect to database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!initializeEconomy()) {
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
     * Fetches an instance of the Vault economy
     * @return Vault economy instance
     */
    public static Economy getEconomy() {
        return economy;
    }

    /**
     * Initializes the Vault economy
     * @return A Vault Economy instance
     */
    private boolean initializeEconomy() {
        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager()
                .getRegistration(Economy.class);
        if (provider == null) return false;
        economy = provider.getProvider();
        return true;
    }

    /**
     * Teleports a user asynchronously with Essentials which ensures /back compatibility.
     * @param player The player to teleport
     * @param location The location to teleport the player
     */
    public static void teleportPlayer(final Player player, final Location location) {
        try {
            essentials.getUser(player).getTeleport().now(location, false,
                    PlayerTeleportEvent.TeleportCause.PLUGIN);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "Teleportation failed- please notify an admin.");
        }
    }

    /**
     * Checks whether a player has completed a specific advancement
     * @param player The player to check advancement status for
     * @param key The advancement name in the "firestarter" namespace
     * @return The status of advancement completion as a boolean
     */
    public static boolean isAdvancementComplete(final Player player, final String key) {
        final NamespacedKey advancementKey = new NamespacedKey("firestarter", key);
        final Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) return true;
        final AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }
}
