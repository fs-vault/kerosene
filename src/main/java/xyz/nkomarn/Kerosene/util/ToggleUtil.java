package xyz.nkomarn.Kerosene.util;

import xyz.nkomarn.Kerosene.data.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Utility class for managing persistent player toggles.
 */
public class ToggleUtil {
    /**
     * Returns the current state of a player's toggle.
     * @param uuid The player to fetch toggle state for.
     * @param key The name of the toggle.
     * @return The current state of the toggle.
     */
    public static boolean getToggleState(UUID uuid, String key) {
        try (Connection connection = PlayerData.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `state` FROM " +
                    "`toggles` WHERE `key` = ? AND `uuid` = ? LIMIT 1), FALSE);")) {
                statement.setString(1, key);
                statement.setString(2, uuid.toString());
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) return result.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sets a player's toggle state to a given state.
     * @param uuid The player to update the toggle for.
     * @param key The name of the toggle.
     * @param state The new state of the toggle.
     */
    public static void setToggleState(UUID uuid, String key, boolean state) {
        try (Connection connection = PlayerData.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM `toggles` WHERE `uuid` = ? AND `key` = ?;")) {
                statement.setString(1, uuid.toString());
                statement.setString(2, key);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `toggles`(`uuid`, `key`, `state`) VALUES (?, ?, ?);")) {
                statement.setString(1, uuid.toString());
                statement.setString(2, key);
                statement.setBoolean(3, state);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
