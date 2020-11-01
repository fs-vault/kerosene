package com.firestartermc.kerosene.data.cache;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.db.RemoteStorage;
import com.firestartermc.kerosene.util.ConcurrentUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public final class ToggleCache {

    private final Kerosene kerosene;
    private final UUID uuid;
    private final PlayerCache<String, Boolean> cache;

    private static final String SELECT_SQL = "SELECT IFNULL((SELECT `state` FROM `toggles` WHERE `key` = ? AND `uuid` = ? LIMIT 1), FALSE);";
    private static final String UPDATE_SQL = "INSERT INTO `toggles` (`uuid`, `key`, `state`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `state` = ?;";

    public ToggleCache(Kerosene kerosene, UUID uuid) {
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
        var statement = connection.prepareStatement("SELECT `key`, `state` FROM `toggles` WHERE `uuid` = ?;");
        statement.setString(1, uuid.toString());
        var result = statement.executeQuery();

        try (connection; statement; result) {
            while (result.next()) {
                cache.put(result.getString(1), result.getBoolean(2));
            }
        }
    }

    public boolean getState(String key) {
        return cache.get(key, () -> {
            var connection = kerosene.getPlayerData().getConnection();
            var statement = connection.prepareStatement(SELECT_SQL);
            statement.setString(1, key);
            statement.setString(2, uuid.toString());
            var result = statement.executeQuery();

            try (connection; statement; result) {
                if (!result.next()) {
                    return false;
                }

                return result.getBoolean(1);
            }
        }).orElse(false);
    }

    public void setState(String key, boolean state) {
        cache.put(key, state);
        ConcurrentUtils.callAsync(() -> {
            var connection = kerosene.getPlayerData().getConnection();
            var statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, uuid.toString());
            statement.setString(2, key);
            statement.setBoolean(3, state);
            statement.setBoolean(4, state);

            try (connection; statement) {
                statement.executeUpdate();
            }

            return null;
        });
    }

    public void toggle(String key) {
        setState(key, !getState(key));
    }
}
