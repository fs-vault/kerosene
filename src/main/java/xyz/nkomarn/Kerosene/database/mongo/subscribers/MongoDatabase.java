package xyz.nkomarn.Kerosene.database.mongo.subscribers;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

public class MongoDatabase {
    private static MongoClient syncClient;
    private static com.mongodb.reactivestreams.client.MongoClient asyncClient;

    public static void connect(MongoClientSettings settings) {
        syncClient = MongoClients.create(settings);
        asyncClient = com.mongodb.reactivestreams.client.MongoClients.create(settings);
    }

    public static void disconnect() {
        syncClient.close();
        asyncClient.close();
    }

    public static MongoClient getSyncClient() {
        return syncClient;
    }

    public static com.mongodb.reactivestreams.client.MongoClient getAsyncClient() {
        return asyncClient;
    }

    public static SyncAsyncCollection<Document> getSyncAsyncCollection(String database, String collection) {
        return new SyncAsyncCollection<>(syncClient.getDatabase(database).getCollection(collection),
            asyncClient.getDatabase(database).getCollection(collection));
    }
}
