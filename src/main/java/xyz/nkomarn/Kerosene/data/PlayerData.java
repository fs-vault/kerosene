package xyz.nkomarn.Kerosene.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerData {
    private static HikariDataSource dataSource;

    public static boolean connect(final String jdbcUrl, final String username, final String password) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
            return true;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static void close() {
        dataSource.close();
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
