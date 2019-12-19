package xyz.nkomarn.Kerosene.velocity;

import com.google.inject.Inject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import xyz.nkomarn.Kerosene.database.mongo.subscribers.MongoDatabase;

import java.io.IOException;
import java.util.Objects;

@Plugin(id = "kerosene", name = "Kerosene", version = "1.2",
    description = "A library of stuff n' things that keep Firestarter ticking!",
    authors = {"TechToolbox (@nkomarn)"})
public class Kerosene {
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Kerosene(final ProxyServer server, final Logger logger) throws IOException {
        this.server = server;
        this.logger = logger;
        Config.loadConfig();
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        final ConfigurationNode mongoNode = Config.getNode("database").getNode("mongo");
        if (mongoNode.getNode("enabled").getBoolean()) {
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(Objects.requireNonNull(
                    mongoNode.getNode("uri").getString())))
                .build();
            MongoDatabase.connect(settings);
        }
    }
}
