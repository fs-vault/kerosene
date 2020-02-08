package xyz.nkomarn.Kerosene.util;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import xyz.nkomarn.Kerosene.database.FlexibleCollection;

/**
 * Utility class to set and reset cooldowns for players.
 */
public class Cooldown {
    private final FlexibleCollection<Document> collection;

    public Cooldown(FlexibleCollection<Document> collection) {
        this.collection = collection;
    }

    public long getCooldown(String uuid, String type) {
        Bson filter = Filters.eq("_id", uuid);
        Document player = collection.sync().find(filter).first();
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
        collection.sync().updateOne(filter, update, options);
    }
}
