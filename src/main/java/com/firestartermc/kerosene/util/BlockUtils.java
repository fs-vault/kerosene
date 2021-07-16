package com.firestartermc.kerosene.util;

import io.papermc.lib.PaperLib;
import net.minecraft.core.BlockPosition;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.CompletableFuture;

/**
 * Miscellaneous block utility methods.
 * <p>
 * This class provides methods for interacting with blocks. It also
 * provides easy-to-use, thread-safe utilities for safety checking
 * block locations, intended to be used for teleportation checks.
 *
 * @author Firestarter Minecraft Servers
 * @since 5.0
 */
@ThreadSafe
public final class BlockUtils {

    private BlockUtils() {
    }

    /**
     * Runs safety checks on a given floor block, ensuring that there
     * is enough space for a player to safely exist in the block's
     * location. The floor block, as well as one block above, is
     * checked to make sure they are breathable. Otherwise, the check
     * returns a failure.
     * <p>
     * This check is intended to be used as a safety check prior to
     * teleporting the player to the floor block's location.
     *
     * @param floor the floor block
     * @return the result of the safety check
     * @since 5.0
     */
    public static boolean checkSafety(@NotNull Block floor) {
        var legs = floor.getLocation().add(0, 1, 0).getBlock();
        return floor.getType().isSolid() && legs.getType() == Material.AIR;
    }

    /**
     * An asynchronous block safety check which loads the given floor
     * block's chunk asynchronously before running checks. This ensures
     * that no synchronous chunkloading occurs when running these checks.
     * <p>
     * Effectively runs {@link BlockUtils#checkSafety(Block)} asynchronously
     * and returns a {@link CompletableFuture<Boolean>} that completes with
     * the result of the safety check.
     *
     * @param floor the floor block
     * @return the result of the safety check
     * @since 5.0
     */
    @NotNull
    public static CompletableFuture<Boolean> checkSafetyAsync(@NotNull Block floor) {
        return PaperLib.getChunkAtAsync(floor.getLocation()).thenApply(chunk -> checkSafety(floor));
    }
}
