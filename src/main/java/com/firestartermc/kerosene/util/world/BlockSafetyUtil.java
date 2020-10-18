package com.firestartermc.kerosene.util.world;

import io.papermc.lib.PaperLib;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Utility class for managing and updating player locations.
 * Includes methods for asynchronous teleportation using PaperLib.
 */
public final class BlockSafetyUtil {

    private BlockSafetyUtil() {
    }

    /**
     * Asynchronously checks if a location has enough space for a player.
     * This is intended to be used as a teleport safety check.
     *
     * @param floor The block the player is going to be standing on.
     * @return A future of the safety check result.
     */
    public static CompletableFuture<Boolean> checkSafety(@NotNull Block floor) {
        return PaperLib.getChunkAtAsync(floor.getLocation()).thenApply(a -> {
            var head = floor.getLocation().add(0, 2, 0).getBlock();
            var legs = floor.getLocation().add(0, 1, 0).getBlock();

            return floor.getType().isSolid()
                    && head.getType() == Material.AIR
                    && legs.getType() == Material.AIR;
        });
    }

    /**
     * Synchronously checks if a location has enough space for a player.
     * This is intended to be used as a teleport safety check.
     *
     * @param floor The block the player is going to be standing on.
     * @return The safety check result.
     */
    public static boolean checkSafetySync(@NotNull Block floor) {
        var head = floor.getLocation().add(0, 2, 0).getBlock();
        var legs = floor.getLocation().add(0, 1, 0).getBlock();
        return floor.getType().isSolid()
                && head.getType() == Material.AIR
                && legs.getType() == Material.AIR;
    }
}
