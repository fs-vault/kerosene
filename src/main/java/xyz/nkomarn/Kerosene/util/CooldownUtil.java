package xyz.nkomarn.Kerosene.util;

import org.bukkit.Bukkit;
import xyz.nkomarn.Kerosene.data.PlayerData;
import xyz.nkomarn.Kerosene.util.cache.PlayerCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for managing cross-server player cooldowns.
 */
public class CooldownUtil {

    /**
     * A cache containing the last time a player's cooldown was reset in milliseconds.
     */
    public static final PlayerCache<String, Long> CACHE = new PlayerCache<>();

    /**
     * Returns the last time a player's cooldown was reset in milliseconds.
     * @param uuid The player for which to fetch cooldown status.
     * @param key The name of the cooldown.
     * @return The last time the cooldown was reset in milliseconds.
     */
    public static long getCooldown(UUID uuid, String key) {
        return CACHE.get(uuid, key, () -> {
            Bukkit.getPlayer(uuid).sendMessage("Request to database");

            try (Connection connection = PlayerData.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `cooldown` FROM " +
                        "`cooldowns` WHERE `key` = ? AND `uuid` = ? LIMIT 1), 0);")) {
                    statement.setString(1, key);
                    statement.setString(2, uuid.toString());
                    try (ResultSet result = statement.executeQuery()) {
                        if (result.next()) return result.getLong(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0L;
        });
    }

    /**
     * Sets a player's cooldown to the current time in milliseconds, effectively resetting it.
     * @param uuid The player for which to reset cooldown.
     * @param key The name of the cooldown.
     */
    public static void resetCooldown(UUID uuid, String key) {

        try (Connection connection = PlayerData.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM `cooldowns` WHERE `uuid` = ? AND `key` = ?;")) {
                statement.setString(1, uuid.toString());
                statement.setString(2, key);
                statement.executeUpdate();
            }

            Long currentTime = System.currentTimeMillis();

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `cooldowns`(`uuid`, `key`, " +
                    "`cooldown`) VALUES (?, ?, ?);")) {
                statement.setString(1, uuid.toString());
                statement.setString(2, key);
                statement.setLong(3, currentTime);
                statement.executeUpdate();
            }

            CACHE.put(uuid, key, currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns how many minutes have passed since the cooldown was set.
     * @param cooldown The cooldown value.
     * @return Minutes have passed since the cooldown was set.
     */
    public static long cooldownToMinutes(long cooldown) {
        return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - cooldown);
    }
}
