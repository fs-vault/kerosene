package xyz.nkomarn.Kerosene;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.nkomarn.Kerosene.database.MongoDatabase;
import xyz.nkomarn.Kerosene.util.Config;

public class KeroseneBungee extends Plugin {
    private static KeroseneBungee kerosene;

    public void onEnable() {
        kerosene = this;
        Config.loadConfig();

        String mongoURI = Config.getString("database.uri");
        if (mongoURI.length() > 0) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(mongoURI))
                    .build();
            MongoDatabase.connect(settings);
        }
    }

    public void onDisable() {
        MongoDatabase.close();
    }

    public static KeroseneBungee getKerosene() {
        return kerosene;
    }
}
