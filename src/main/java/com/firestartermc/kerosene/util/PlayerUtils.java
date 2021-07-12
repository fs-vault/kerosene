package com.firestartermc.kerosene.util;

import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import com.firestartermc.kerosene.Kerosene;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.CompletableFuture;

/**
 * Miscellaneous {@link Player} utility methods.
 * <p>
 * This class provides methods for common actions that are ran on
 * players. It also provides easy-to-use, thread-safe utilities
 * that can be safely used in the {@link BukkitScheduler} or executors.
 *
 * @author Firestarter Minecraft Servers
 * @see Player
 * @since 4.0
 */
@ThreadSafe
public final class PlayerUtils {

    private PlayerUtils() {
    }

    /**
     * Attempts to add an {@link ItemStack} to a given player's inventory. In
     * the case that the player's inventory is full, the ItemStack is dropped
     * at the player's current location in the world. The dropped stack will
     * be bound to the player, which prevents other players from picking up the
     * dropped stack.
     *
     * @param player    the player to give the item to
     * @param itemStack the item to give to the player
     * @since 4.0
     */
    public static void giveOrDropItem(@NotNull Player player, @NotNull ItemStack itemStack) {
        ConcurrentUtils.ensureMain(() -> {
            var overflow = player.getInventory().addItem(itemStack);
            for (var item : overflow.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item).setOwner(player.getUniqueId());
            }
        });
    }

    /**
     * Pushes a given player away from the given {@code location} by adding velocity
     * in the opposite direction of the;
     * location. This effectively launches the
     * player into the air. The distance the player is launched can be manipulated with
     * the {@code velocityMultiplier} parameter. Additionally, extra y-axis velocity
     * can be added to the player by modifying the {@code heightMultiplier} parameter.
     *
     * @param player             the player to apply velocity to
     * @param location           the location to push the player away from
     * @param velocityMultiplier push-back velocity multiplier
     * @param heightMultiplier   y-axis velocity amplification
     * @since 4.0
     */
    public static void pushAway(@NotNull Player player, @NotNull Location location, double velocityMultiplier, double heightMultiplier) {
        var velocityVector = location.toVector().normalize().multiply(velocityMultiplier).setY(heightMultiplier);
        ConcurrentUtils.ensureMain(() -> {
            if (player.isInsideVehicle()) {
                player.getVehicle().setVelocity(velocityVector);
            } else {
                player.setVelocity(velocityVector);
            }
        });
    }

    @NotNull
    @Deprecated(forRemoval = true)
    public static CompletableFuture<Boolean> teleportAsync(@NotNull Player player, @NotNull Location location) {
        return TeleportUtils.teleportAsync(player, location);
    }
}
