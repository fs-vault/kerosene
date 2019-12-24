package xyz.nkomarn.Kerosene.velocity;

import com.google.inject.Inject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import xyz.nkomarn.Kerosene.database.mongo.MongoDatabase;
import xyz.nkomarn.Kerosene.database.redis.RedisDatabase;

import java.io.IOException;
import java.util.Objects;

@Plugin(id = "kerosene", name = "Kerosene", version = "1.2",
    description = "A library of stuff n' things that keep Firestarter ticking!",
    authors = {"TechToolbox (@nkomarn)"})
public class Kerosene {
    private final ProxyServer server;
    private static Logger logger;
    private static String serverId;

    @Inject
    public Kerosene(final ProxyServer server, final Logger logger) throws IOException {
        this.server = server;
        Kerosene.logger = logger;
        Config.loadConfig();
        serverId = Config.getNode("server-id").getString();
        logger.info(String.format("Running with identifier '%s'.", serverId));
    }

    @Subscribe
    public void onProxyInitialized(final ProxyInitializeEvent event) {
        final ConfigurationNode redisNode = Config.getNode("database").getNode("redis");
        if (redisNode.getNode("enabled").getBoolean()) {
            logger.info("Initializing Redis database.");
            final String host = redisNode.getNode("host").getString();
            final int port = redisNode.getNode("port").getInt();
            final String password = redisNode.getNode("password").getString();
            final int maxConnections = redisNode.getNode("max-connections").getInt();
            RedisDatabase.connect(host, port, password, maxConnections);
        }

        final ConfigurationNode mongoNode = Config.getNode("database").getNode("mongo");
        if (mongoNode.getNode("enabled").getBoolean()) {
            logger.info("Initializing Mongo database.");
            final MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(Objects.requireNonNull(
                            mongoNode.getNode("uri").getString())))
                    .build();
            MongoDatabase.connect(settings);
        }
    }

    @Subscribe
    public void onProxyShutdown(final ProxyShutdownEvent event) {
        final ConfigurationNode redisNode = Config.getNode("database").getNode("redis");
        if (redisNode.getNode("enabled").getBoolean()) {
            RedisDatabase.disconnect();
        }

        final ConfigurationNode mongoNode = Config.getNode("database").getNode("mongo");
        if (mongoNode.getNode("enabled").getBoolean()) {
            MongoDatabase.disconnect();
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static String getServerId() {
        return serverId;
    }
}
