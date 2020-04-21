package xyz.nkomarn.Kerosene.data;

import xyz.nkomarn.Kerosene.Kerosene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database class which allows access to
 * connections to a local SQLite database.
 */
public class LocalStorage {
    private static final String fileLocation = String.format("%s%slocal.db",
            Kerosene.getKerosene().getDataFolder(), File.separator);

    /**
     * Attempts to load the local SQLite database.
     * @return Whether loading was successful.
     */
    public static boolean initialize() {
        if (Files.exists(Paths.get(fileLocation))) return true;

        try {
            Files.createDirectories(Paths.get(Kerosene.getKerosene()
                    .getDataFolder().getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Connection connection;
        try {
            connection = getConnection();
            if (connection == null) return false;
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches a new connection to the local database.
     * @return An available connection to the playerdata database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s", fileLocation));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database driver.", e);
        }
    }

    /**
     * Creates local tables in the database.
     */
    public static void createTables() {
        Connection connection = null;

        try {
            final String togglesQuery = "CREATE TABLE IF NOT EXISTS toggles (uuid TEXT PRIMARY KEY, key TEXT NOT" +
                    " NULL, state BOOLEAN NOT NULL CHECK (state IN (0,1));";

            connection = LocalStorage.getConnection();
            PreparedStatement statement = connection.prepareStatement(togglesQuery);
            statement.execute();
            connection.close();
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
