package xyz.nkomarn.Kerosene;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import xyz.nkomarn.Kerosene.database.Database;
import xyz.nkomarn.Kerosene.util.Metrics;

import java.net.InetSocketAddress;

public class Kerosene extends JavaPlugin {

    public static Kerosene instance;
    private Server server;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(getConfig().getString("database")))
                .build();
        Database.connect(settings);

        // Start Prometheus exporter
        InetSocketAddress address = new InetSocketAddress("0.0.0.0",
                getConfig().getInt("exporter"));
        server = new Server(address);
        server.setHandler(new Metrics());

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        Database.disconnect();

        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
