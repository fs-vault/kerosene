package xyz.nkomarn.Kerosene.util;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import xyz.nkomarn.Kerosene.database.FlexibleCollection;

/**
 * Utility class to create persistent toggles for players.
 */
public class Toggle {
    private final FlexibleCollection<Document> collection;

    public Toggle(FlexibleCollection<Document> collection) {
        this.collection = collection;
    }

    public boolean getState(String uuid, String type) {
        Bson filter = Filters.eq("_id", uuid);
        Document player = collection.sync().find(filter).first();
        if (!player.containsKey("toggles")) {
            return true;
        }
        Document toggles = (Document) player.get("toggles");
        return (!toggles.containsKey(type)) ? true : toggles.getBoolean(type);
    }

    public void setState(String uuid, String type, boolean state) {
        Bson filter = Filters.eq("_id", uuid);
        Bson update = new Document("$set", new Document().append("toggles",
                new Document().append(type, state)));
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.sync().updateOne(filter, update, options);
    }
}
