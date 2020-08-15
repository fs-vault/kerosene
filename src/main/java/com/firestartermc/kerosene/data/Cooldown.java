package com.firestartermc.kerosene.data;

import com.firestartermc.kerosene.data.cache.PlayerCache;
import com.firestartermc.kerosene.data.db.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for managing cross-server player cooldowns.
 */
public final class Cooldown {

    /**
     * A cache containing the last time a player's cooldown was reset in milliseconds.
     */
    public static final PlayerCache<String, Long> CACHE = new PlayerCache<>();

    private Cooldown() {
    }

    /**
     * Returns the last time a player's cooldown was reset in milliseconds.
     *
     * @param uuid The player for which to fetch cooldown status.
     * @param key  The name of the cooldown.
     * @return The last time the cooldown was reset in milliseconds.
     */
    public static long getCooldown(UUID uuid, String key) {
        return CACHE.get(uuid, key, () -> {
            try (Connection connection = PlayerData.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `cooldown` FROM `cooldowns` WHERE `key` = ? AND `uuid` = ? LIMIT 1), 0);")) {
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
        }).orElse(0L);
    }

    /**
     * Sets a player's cooldown to the current time in milliseconds, effectively resetting it.
     *
     * @param uuid The player for which to reset cooldown.
     * @param key  The name of the cooldown.
     */
    public static void resetCooldown(UUID uuid, String key) {
        long currentTime = System.currentTimeMillis();

        try (Connection connection = PlayerData.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `cooldowns` (`uuid`, `key`, `cooldown`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `cooldown` = ?;")) {
                statement.setString(1, uuid.toString());
                statement.setString(2, key);
                statement.setLong(3, currentTime);
                statement.setLong(4, currentTime);
                statement.executeUpdate();
            }

            CACHE.put(uuid, key, currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns how many minutes have passed since the cooldown was set.
     *
     * @param cooldown The cooldown value.
     * @return Minutes have passed since the cooldown was set.
     */
    public static long cooldownToMinutes(long cooldown) {
        return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - cooldown);
    }
}
