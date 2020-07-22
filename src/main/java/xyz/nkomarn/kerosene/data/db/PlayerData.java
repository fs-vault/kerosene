package xyz.nkomarn.kerosene.data.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database class which allows pooled access
 * to playerdata database connections.
 */
public class PlayerData {

    private static HikariDataSource DATA_SOURCE;

    /**
     * Connects the pool to the remote database using provided credentials.
     *
     * @param jdbcUrl  The JDBC URL for the remote database.
     * @param username The username for the remote database user.
     * @param password The password for the remote database user.
     * @return Whether a connection has been successfully established.
     */
    public static boolean connect(final String jdbcUrl, final String username, final String password) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            DATA_SOURCE = new HikariDataSource(config);
            return true;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Closes the connection pool and all connections.
     */
    public static void close() {
        if (!DATA_SOURCE.isClosed()) {
            DATA_SOURCE.close();
        }
    }

    /**
     * Fetches an available connection from the connection pool.
     *
     * @return An available connection to the playerdata database.
     * @throws SQLException Errors that have occurred while fetching a connection.
     */
    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
