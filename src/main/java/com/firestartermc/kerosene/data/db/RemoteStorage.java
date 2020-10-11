package com.firestartermc.kerosene.data.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database class which allows pooled access to remote database connections.
 */
public class RemoteStorage {

    private final HikariConfig config;
    private HikariDataSource dataSource;

    public RemoteStorage(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.config = config;
    }

    @NotNull
    public RemoteStorage connect() {
        dataSource = new HikariDataSource(config);
        return this;
    }

    public boolean isConnected() {
        return dataSource != null && !dataSource.isClosed();
    }

    public void close() {
        if (isConnected()) {
            dataSource.close();
        }
    }

    @NotNull
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
