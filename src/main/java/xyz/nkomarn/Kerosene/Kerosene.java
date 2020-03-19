package xyz.nkomarn.Kerosene;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.database.FlexibleCollection;
import xyz.nkomarn.Kerosene.database.MongoDatabase;
import xyz.nkomarn.Kerosene.util.Cooldowns;
import xyz.nkomarn.Kerosene.util.Toggles;

public class Kerosene extends JavaPlugin {
    private static Kerosene kerosene;
    private static FlexibleCollection<Document> playerData;
    private static Cooldowns cooldowns;
    private static Toggles toggles;

    public void onEnable() {
        kerosene = this;
        saveDefaultConfig();

        String mongoURI = getConfig().getString("database.uri");
        getLogger().info("Initializing Mongo.");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoURI))
                .build();
        MongoDatabase.connect(settings);

        playerData = MongoDatabase.getFlexibleCollection(getConfig()
                .getString("database.database"), "players");
        cooldowns = new Cooldowns();
        toggles = new Toggles();
    }

    public void onDisable() {
        MongoDatabase.close();
    }

    public static Kerosene getKerosene() {
        return kerosene;
    }

    public static FlexibleCollection<Document> getPlayerData() {
        return playerData;
    }

    public static Cooldowns getCooldowns() {
        return cooldowns;
    }

    public static Toggles getToggles() {
        return toggles;
    }
}
