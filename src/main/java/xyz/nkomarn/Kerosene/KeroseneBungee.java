package xyz.nkomarn.Kerosene;

import com.google.common.io.ByteStreams;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.nkomarn.Kerosene.database.Database;

import java.io.*;

public class KeroseneBungee extends Plugin {

    public void onEnable() {
        Configuration configuration = null;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                    OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (configuration.getString("database").length() > 0) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(configuration.getString("database")))
                    .build();
            Database.connect(settings);
        }
    }

    public void onDisable() {

    }

}
