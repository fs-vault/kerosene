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
 * This class contains methods for common actions that are ran on
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
     * Pushes a given player away from their current location by adding velocity
     * in the opposite direction of their location. This effectively launches the
     * player into the air. The distance the player is launched can be manipulated with
     * the {@code velocityMultiplier} parameter. Additionally, extra y-axis velocity
     * can be added to the player by modifying the {@code heightMultiplier} parameter.
     *
     * @param player             the player to apply velocity to
     * @param velocityMultiplier push-back velocity multiplier
     * @param heightMultiplier   y-axis velocity amplification
     * @since 4.0
     */
    public static void pushAway(@NotNull Player player, double velocityMultiplier, int heightMultiplier) {
        var locationVector = player.getLocation().toVector();
        var velocityVector = locationVector.normalize().multiply(velocityMultiplier).setY(heightMultiplier);
        ConcurrentUtils.ensureMain(() -> {
            if (player.isInsideVehicle()) {
                player.getVehicle().setVelocity(velocityVector);
            } else {
                player.setVelocity(velocityVector);
            }
        });
    }

    /**
     * Asynchronously loads the chunk at the given {@code location} and teleports the given
     * {@code player} to the location once the chunk has loaded. This eliminates any
     * chunkloading slowdowns when teleporting players.
     * <p>
     * If EssentialsX is installed on the server, asynchronous teleportation is delegated
     * to EssentialsX so that players are able to use /back between teleportation locations.
     * Otherwise, {@link PaperLib#teleportAsync(Entity, Location, PlayerTeleportEvent.TeleportCause)}
     * is used to load chunks asynchronously and teleport the player.
     * <p>
     * A {@link CompletableFuture} is returned immediately, and is completed once the player
     * has successfully teleported to the given location. If teleportation fails, the future
     * is completed exceptionally.
     *
     * @param player   the player to teleport
     * @param location the location to teleport to
     * @return future which completes on teleportation success
     * @see PaperLib
     * @since 5.0
     */
    @NotNull
    public static CompletableFuture<Boolean> teleportAsync(@NotNull Player player, @NotNull Location location) {
        var cause = PlayerTeleportEvent.TeleportCause.PLUGIN;
        if (Kerosene.getKerosene().getEssentials() == null) {
            return PaperLib.teleportAsync(player, location, cause);
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Kerosene.getKerosene().getEssentials().getUser(player).getAsyncTeleport().now(location, false, cause, future);
        return future;
    }
}
