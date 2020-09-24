package com.firestartermc.kerosene.data.cache;

import com.firestartermc.kerosene.data.db.PlayerData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

public final class CooldownCache {

    private final Player player;
    private final PlayerCache<String, Long> cache;

    public CooldownCache(Player player) {
        this.player = player;
        this.cache = new PlayerCache<>();
    }

    @NotNull
    public CompletableFuture<Long> getCooldown(String key) {
        return cache.get(player.getUniqueId(), key, () -> {
            try {
                Connection connection = PlayerData.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `cooldown` FROM `cooldowns` WHERE `key` = ? AND `uuid` = ? LIMIT 1), 0);");
                statement.setString(1, key);
                statement.setString(2, player.getUniqueId().toString());
                ResultSet result = statement.executeQuery();

                try (connection; statement; result) {
                    if (!result.next()) {
                        return 0L;
                    }

                    return result.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return System.currentTimeMillis();
        });
    }

    public CompletableFuture<Void> setCooldown(String key, long value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Connection connection = PlayerData.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO `cooldowns` (`uuid`, `key`, `cooldown`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `cooldown` = ?;");
                statement.setString(1, player.getUniqueId().toString());
                statement.setString(2, key);
                statement.setLong(3, value);
                statement.setLong(4, value);

                try (connection; statement) {
                    statement.executeUpdate();
                    return null;
                }
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        });
    }

    // TODO move to some sorta time util since this is now just a cache
    public static long cooldownToMinutes(long cooldown) {
        return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - cooldown);
    }
}
