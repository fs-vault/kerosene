package xyz.nkomarn.Kerosene.spigot;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import xyz.nkomarn.Kerosene.database.mongo.subscribers.MongoDatabase;

import java.net.InetSocketAddress;
import java.util.Objects;

public class Kerosene extends JavaPlugin {
    private static Kerosene instance;
    private Server server;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        if (getConfig().getBoolean("database.mongo.enabled")) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(Objects.requireNonNull(
                            getConfig().getString("database.mongo.uri"))))
                    .build();
            MongoDatabase.connect(settings);
        }

        if (getConfig().getBoolean("exporter.enabled")) {
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
}
