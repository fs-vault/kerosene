package com.firestartermc.kerosene.util;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.firestartermc.kerosene.Kerosene;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.firestartermc.kerosene.util.ConcurrentUtils.ensureMain;

/**
 * Miscellaneous {@link Player} teleportation methods.
 * <p>
 * This class provides methods to teleport players both syncronously
 * and asynchronously (if supported). These methods are fully thread-
 * safe.
 *
 * @author Firestarter Minecraft Servers
 * @since 5.1.4
 */
@ThreadSafe
public class TeleportUtils {

    private static final PlayerTeleportEvent.TeleportCause TELEPORT_CAUSE = PlayerTeleportEvent.TeleportCause.PLUGIN;

    private TeleportUtils() {
    }

    @NotNull
    public static Optional<Location> getLastLocation(@NotNull Player player) {
        return getEssentials()
                .map(essentials -> essentials.getUser(player))
                .map(User::getLastLocation);
    }

    public static void setLastLocation(@NotNull Player player) {
        getEssentials()
                .map(essentials -> essentials.getUser(player))
                .ifPresent(User::setLastLocation);
    }

    public static void teleport(@NotNull Player player, @NotNull Location location) {
        setLastLocation(player);

        getEssentials().ifPresentOrElse(essentials -> {
            try {
                essentials.getUser(player).getTeleport().now(location, false, TELEPORT_CAUSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, () -> player.teleport(location));
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
        var essentials = getEssentials();

        if (essentials.isEmpty()) {
            return PaperLib.teleportAsync(player, location, TELEPORT_CAUSE);
        }

        var user = essentials.get().getUser(player);
        var future = new CompletableFuture<Boolean>();

        ensureMain(() -> user.getAsyncTeleport().now(location, false, TELEPORT_CAUSE, future));
        return future;
    }

    @NotNull
    private static Optional<Essentials> getEssentials() {
        return Optional.ofNullable(Kerosene.getKerosene().getEssentials());
    }
}
