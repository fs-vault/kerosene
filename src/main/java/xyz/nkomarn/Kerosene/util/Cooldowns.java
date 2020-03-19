package xyz.nkomarn.Kerosene.util;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import xyz.nkomarn.Kerosene.Kerosene;

/**
 * Utility class to set and reset cooldowns for players.
 */
public class Cooldowns {
    public long getCooldown(String uuid, String type) {
        Bson filter = Filters.eq("_id", uuid);
        Document player = Kerosene.getPlayerData().sync().find(filter).first();
        if (!player.containsKey("cooldowns")) {
            return 0;
        }
        Document cooldowns = (Document) player.get("cooldowns");
        return (!cooldowns.containsKey(type)) ? 0 : cooldowns.getLong(type);
    }

    public void resetCooldown(String uuid, String type) {
        Bson filter = Filters.eq("_id", uuid);
        Bson update = new Document("$set", new Document().append("cooldowns",
                new Document().append(type, System.currentTimeMillis())));
        UpdateOptions options = new UpdateOptions().upsert(true);
        Kerosene.getPlayerData().sync().updateOne(filter, update, options);
    }
}
