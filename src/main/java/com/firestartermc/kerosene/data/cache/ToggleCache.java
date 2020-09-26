package com.firestartermc.kerosene.data.cache;

import com.firestartermc.kerosene.data.db.PlayerData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public final class ToggleCache {

    private final Player player;
    private final PlayerCache<String, Boolean> cache;

    private ToggleCache(Player player) {
        this.player = player;
        this.cache = new PlayerCache<>();
    }

    public CompletableFuture<Boolean> getState(String key) {
        return cache.get(key, () -> {
            try {
                Connection connection = PlayerData.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `state` FROM `toggles` WHERE `key` = ? AND `uuid` = ? LIMIT 1), FALSE);");
                statement.setString(1, key);
                statement.setString(2, player.getUniqueId().toString());
                ResultSet result = statement.executeQuery();

                try (connection; statement; result) {
                    if (!result.next()) {
                        return false;
                    }

                    return result.getBoolean(1);
                }
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<Void> setState(String key, boolean state) {
        cache.put(key, state);
        return CompletableFuture.supplyAsync(() -> {
           try {
               Connection connection = PlayerData.getConnection();
               PreparedStatement statement = connection.prepareStatement("INSERT INTO `toggles` (`uuid`, `key`, `state`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `state` = ?;");
               statement.setString(1, player.getUniqueId().toString());
               statement.setString(2, key);
               statement.setBoolean(3, state);
               statement.setBoolean(4, state);

               try (connection; statement) {
                   statement.executeUpdate();
                   return null;
               }
           } catch (SQLException e) {
               throw new CompletionException(e);
           }
        });
    }
}
