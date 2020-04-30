package xyz.nkomarn.Kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import redis.clients.jedis.Jedis;
import xyz.nkomarn.Kerosene.Kerosene;
import xyz.nkomarn.Kerosene.data.Redis;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for hiding players and checking
 * whether players are visible to others.
 */
public class VanishUtil {
    /**
     * Hides a player from all online players that don't have the permission "firestarter.vanish."
     * @param player The player to hide.
     */
    public static void hidePlayer(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Kerosene.getKerosene(), () -> {
            try (Jedis jedis = Redis.getResource()) {
                jedis.sadd("vanish", player.getUniqueId().toString());
            }
        });

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!onlinePlayer.hasPermission("firestarter.vanish")) {
                onlinePlayer.hidePlayer(Kerosene.getKerosene(), player);
            }
        });

        player.setMetadata("vanished", new FixedMetadataValue(Kerosene.getKerosene(), false));
        player.setSleepingIgnored(true);
        player.setCollidable(false);
    }

    /**
     * Makes a player visible to all online players.
     * @param player The player to hide.
     */
    public static void showPlayer(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Kerosene.getKerosene(), () -> {
            try (Jedis jedis = Redis.getResource()) {
                jedis.srem("vanish", player.getUniqueId().toString());
            }
        });

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(Kerosene.getKerosene(), player));

        player.setMetadata("vanished", new FixedMetadataValue(Kerosene.getKerosene(), false));
        player.setSleepingIgnored(false);
        player.setCollidable(true);
    }

    /**
     * Returns a Set of all the currently vanished players both online and offline.
     * @return A set of player UUIDs are that vanished.
     */
    public static Set<UUID> getTotalVanishedPlayers() {
        Set<UUID> vanishedPlayers = new HashSet<>();
        try (Jedis jedis = Redis.getResource()) {
            jedis.smembers("vanish").forEach(uuid -> vanishedPlayers.add(UUID.fromString(uuid)));
        }
        return vanishedPlayers;
    }

    /**
     * Returns a Set of all the currently vanished players that are online.
     * @return A set of player UUIDs that are vanished and online.
     */
    public static Set<UUID> getOnlineVanishedPlayers() {
        return getTotalVanishedPlayers().stream()
                .filter(uuid -> Bukkit.getOfflinePlayer(uuid).isOnline())
                .collect(Collectors.toSet());
    }

    /**
     * Checks whether a given player is vanished.
     * @param player The player object for which to check vanished state.
     * @return Whether the given player is vanished.
     */
    public static boolean isVanished(final OfflinePlayer player) {
        try (Jedis jedis = Redis.getResource()) {
            return jedis.sismember("vanish", player.getUniqueId().toString());
        }
    }
}
