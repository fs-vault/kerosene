package xyz.nkomarn.Kerosene.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.nkomarn.Kerosene.Kerosene;

/**
 * Utility class for managing and updating player locations.
 * Includes methods for asynchronous teleportation using PaperLib.
 */
public final class LocationUtil {

    /**
     * Private constructor preventing the instantiation of this static class.
     */
    private LocationUtil() { }

    /**
     * Teleports a user asynchronously with Essentials which ensures /back compatibility.
     * @param player The player to teleport.
     * @param location The location to teleport the player.
     */
    public static void teleportPlayer(Player player, Location location) {
        try {
            player.teleport(location);
            /*Kerosene.getEssentials().getUser(player).getTeleport().now(location, false,
                    PlayerTeleportEvent.TeleportCause.PLUGIN);*/
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "Teleportation failed- please notify an admin.");
        }
    }

    /**
     * Checks if a location has enough space for a player.
     * @param floor The block the player is going to be standing on.
     * @return Whether the location is safe or not.
     */
    public static boolean isLocationSafe(final Block floor) {
        Block legs = floor.getLocation().add(0, 1,0).getBlock();
        Block head = floor.getLocation().add(0, 2,0).getBlock();

        return (floor.getType().isSolid() && legs.getType() == Material.AIR
                && head.getType() == Material.AIR);
    }
}
