package xyz.nkomarn.Kerosene;

import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import xyz.nkomarn.Kerosene.database.Database;
import xyz.nkomarn.Kerosene.util.Config;
import xyz.nkomarn.Kerosene.util.Metrics;

import java.net.InetSocketAddress;

public class Kerosene extends JavaPlugin {

    public static Kerosene instance;
    private Server server;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Database.connect();

        // Start Prometheus exporter
        InetSocketAddress address = new InetSocketAddress("0.0.0.0",
                Config.getInteger("exporter"));
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
