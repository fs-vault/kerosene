package xyz.nkomarn.Kerosene.util;

import xyz.nkomarn.Kerosene.data.LocalStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public static boolean getToggleState(final UUID uuid, final String key) {
        Connection connection = null;

        try {
            connection = LocalStorage.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT IFNULL((SELECT `state` FROM " +
                    "`toggles` WHERE `uuid` = ? LIMIT 1), FALSE);");
            statement.setString(1, uuid.toString());
            return statement.executeQuery().getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * Sets a player's toggle state to a given state.
     * @param uuid The player to update the toggle for.
     * @param key The name of the toggle.
     * @param state The new state of the toggle.
     */
    public static void setToggleState(final UUID uuid, final String key, final boolean state) {
        Connection connection = null;

        try {
            connection = LocalStorage.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `toggles`(`uuid`, `key`, " +
                    "`state`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `state` = ?;");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
