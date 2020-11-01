package com.firestartermc.kerosene.data.cache;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.util.ConcurrentUtils;

import java.sql.SQLException;
import java.util.UUID;

public final class CooldownCache {

    private final Kerosene kerosene;
    private final UUID uuid;
    private final PlayerCache<String, Long> cache;

    private static final String CACHE_SQL = "SELECT `key`, `cooldown` FROM `cooldowns` WHERE `uuid` = ?;";
    private static final String SELECT_SQL = "SELECT IFNULL((SELECT `cooldown` FROM `cooldowns` WHERE `key` = ? AND `uuid` = ? LIMIT 1), 0);";
    private static final String UPDATE_SQL = "INSERT INTO `cooldowns` (`uuid`, `key`, `cooldown`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `cooldown` = ?;";

    public CooldownCache(Kerosene kerosene, UUID uuid) {
        this.kerosene = kerosene;
        this.uuid = uuid;
        this.cache = new PlayerCache<>();

        ConcurrentUtils.callAsync(() -> {
            cache();
            return null;
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    private void cache() throws SQLException {
        var connection = kerosene.getPlayerData().getConnection();
        var statement = connection.prepareStatement(CACHE_SQL);
        statement.setString(1, uuid.toString());
        var result = statement.executeQuery();

        try (connection; statement; result) {
            while (result.next()) {
                cache.put(result.getString(1), result.getLong(2));
            }
        }
    }

    public long getCooldown(String key) {
        return cache.get(key, () -> {
            var connection = kerosene.getPlayerData().getConnection();
            var statement = connection.prepareStatement(SELECT_SQL);
            statement.setString(1, key);
            statement.setString(2, uuid.toString());
            var result = statement.executeQuery();

            try (connection; statement; result) {
                if (!result.next()) {
                    return 0L;
                }

                return result.getLong(1);
            }
        }).orElse(System.currentTimeMillis());
    }

    public void setCooldown(String key, long value) {
        cache.put(key, value);
        ConcurrentUtils.callAsync(() -> {
            var connection = kerosene.getPlayerData().getConnection();
            var statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, uuid.toString());
            statement.setString(2, key);
            statement.setLong(3, value);
            statement.setLong(4, value);

            try (connection; statement) {
                statement.executeUpdate();
            }

            return null;
        });
    }

}
