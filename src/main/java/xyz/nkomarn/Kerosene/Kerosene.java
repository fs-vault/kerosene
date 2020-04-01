package xyz.nkomarn.Kerosene;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.command.KeroseneCommand;
import xyz.nkomarn.Kerosene.data.PlayerData;

public class Kerosene extends JavaPlugin {
    private static Essentials essentials;

    @Override
    public void onEnable() {
        if (!PlayerData.connect(getConfig().getString("database.url"), getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {
            getLogger().warning("Failed to connect to database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("essentials");
        getCommand("kerosene").setExecutor(new KeroseneCommand());
    }

    @Override
    public void onDisable() {
        PlayerData.close();
    }

    /**
     * Teleports a user asynchronously with Essentials which ensures /back compatibility.
     * @param player The player to teleport
     * @param location The location to teleport the player
     */
    public static void teleportPlayer(final Player player, final Location location) {
        try {
            essentials.getUser(player).getTeleport().now(location, false, PlayerTeleportEvent.TeleportCause.PLUGIN);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "Teleportation failed- please notify an admin.");
        }
    }
}
