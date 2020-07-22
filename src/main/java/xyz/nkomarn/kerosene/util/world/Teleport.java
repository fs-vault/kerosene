package xyz.nkomarn.kerosene.util.world;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import xyz.nkomarn.kerosene.Kerosene;

import java.util.concurrent.CompletableFuture;

/**
 * Utility class for managing and updating player locations.
 * Includes methods for asynchronous teleportation using PaperLib.
 */
public final class Teleport {

    private Teleport() {
    }

    /**
     * Asynchronously checks if a location has enough space for a player.
     * This is inteded to be used as a teleport safety check.
     *
     * @param floor The block the player is going to be standing on.
     * @return A future of the safety check result.
     */
    public static CompletableFuture<Boolean> checkSafety(@NotNull Block floor) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        PaperLib.getChunkAtAsync(floor.getLocation()).thenAccept(chunk -> {
            Block legs = floor.getLocation().add(0, 1, 0).getBlock();
            Block head = floor.getLocation().add(0, 2, 0).getBlock();

            future.complete(floor.getType().isSolid() && legs.getType() == Material.AIR && head.getType() == Material.AIR);
        });
        return future;
    }

    /**
     * Teleports a player asynchronously with Essentials which ensures /back compatibility.
     *
     * @param player   The player to teleport.
     * @param location The location to teleport the player.
     * @return A future of the teleport result.
     */
    public static CompletableFuture<Boolean> teleportPlayer(@NotNull Player player, @NotNull Location location) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Kerosene.getPool().submit(() -> Kerosene.getEssentials().getUser(player).getAsyncTeleport().now(
                location,
                false,
                PlayerTeleportEvent.TeleportCause.PLUGIN,
                future
        ));
        return future;
    }
}
