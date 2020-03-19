package xyz.nkomarn.Kerosene;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import net.md_5.bungee.api.plugin.Plugin;
import org.bson.Document;
import xyz.nkomarn.Kerosene.database.FlexibleCollection;
import xyz.nkomarn.Kerosene.database.MongoDatabase;
import xyz.nkomarn.Kerosene.util.Config;

public class KeroseneBungee extends Plugin {
    private static KeroseneBungee kerosene;
    private static FlexibleCollection<Document> playerData;

    public void onEnable() {
        kerosene = this;
        Config.loadConfig();

        String mongoURI = Config.getString("database.uri");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoURI))
                .build();
        MongoDatabase.connect(settings);


    }

    public void onDisable() {
        MongoDatabase.close();
    }

    public static KeroseneBungee getKerosene() {
        return kerosene;
    }

    public static FlexibleCollection<Document> getPlayerData() {
        return playerData;
    }
}
