package com.firestartermc.kerosene.data.db;

import org.jetbrains.annotations.NotNull;
import com.firestartermc.kerosene.Kerosene;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility for easily creating SQLite databases which allow plugins to store data locally.
 * This class should be instantiated per database file.
 */
public class LocalStorage {

    private final String name, location;

    public LocalStorage(@NotNull String name) {
        this.name = name;
        this.location = String.format("%s/dbs/%s.db", Kerosene.getKerosene().getDataFolder().toString(), name);

        try {
            Files.createDirectories(Kerosene.getKerosene().getDataFolder().toPath().resolve("dbs"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection connection = getConnection()) {
            // Do nothing - connection is successful
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create local database.", e);
        }
    }

    /**
     * Returns a new connection to the local database.
     *
     * @return A new connection to the local database.
     * @throws SQLException Exception that may occur while creating a new connection.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + location);
    }

    /**
     * Returns the name of this local storage database.
     *
     * @return The name of this local storage.
     */
    public String getName() {
        return name;
    }
}
