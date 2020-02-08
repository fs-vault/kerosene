package xyz.nkomarn.Kerosene;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.database.MongoDatabase;

public class Kerosene extends JavaPlugin {
    private static Kerosene kerosene;

    public void onEnable() {
        kerosene = this;
        saveDefaultConfig();

        String mongoURI = getConfig().getString("database.uri");
        if (mongoURI.length() > 0) {
            getLogger().info("Initializing Mongo.");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(mongoURI))
                    .build();
            MongoDatabase.connect(settings);
        }
    }

    public void onDisable() {
        MongoDatabase.close();
    }

    public static Kerosene getKerosene() {
        return kerosene;
    }
}
