package xyz.nkomarn.Kerosene.spigot;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import xyz.nkomarn.Kerosene.database.mongo.subscribers.MongoDatabase;
import xyz.nkomarn.Kerosene.database.redis.RedisDatabase;

import java.net.InetSocketAddress;
import java.util.Objects;

public class Kerosene extends JavaPlugin {
    private static Kerosene instance;
    private Server server;
    private static String serverId;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        serverId = getConfig().getString("server-id");
        getLogger().info(String.format("Running with identifier '%s'.", serverId));

        if (getConfig().getBoolean("database.mongo.enabled")) {
            getLogger().info("Initializing Mongo database.");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(Objects.requireNonNull(
                            getConfig().getString("database.mongo.uri"))))
                    .build();
            MongoDatabase.connect(settings);
        }

        if (getConfig().getBoolean("database.redis.enabled")) {
            getLogger().info("Initializing Redis database.");
            final String host = getConfig().getString("database.redis.host");
            final int port = getConfig().getInt("database.redis.port");
            final String password = getConfig().getString("database.redis.password");
            final int maxConnections = getConfig().getInt("database.redis.max-connections");
            RedisDatabase.connect(host, port, password, maxConnections);
        }

        if (getConfig().getBoolean("exporter.enabled")) {
            getLogger().info("Initializing exporter.");
            InetSocketAddress address = new InetSocketAddress("0.0.0.0",
                    getConfig().getInt("exporter.port"));
            server = new Server(address);
            server.setHandler(new Metrics());
            try {
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDisable() {
        MongoDatabase.disconnect();
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Kerosene getInstance() {
        return instance;
    }

    public static String getServerId() {
        return serverId;
    }
}
