package xyz.nkomarn.Kerosene.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.Document;

import com.mongodb.MongoClientSettings;

/**
 * Represents a sync/async connection to a Mongo database.
 */
public class MongoDatabase {
    private static MongoClient sync;
    private static com.mongodb.reactivestreams.client.MongoClient async;

    public static void connect(MongoClientSettings settings) {
        sync = MongoClients.create(settings);
        async = com.mongodb.reactivestreams.client.MongoClients.create(settings);
    }

    public static void close() {
        sync.close();
        async.close();
    }

    public static MongoClient getSync() {
        return sync;
    }

    public static com.mongodb.reactivestreams.client.MongoClient getAsync() {
        return async;
    }

    public static FlexibleCollection<Document> getFlexibleCollection(String database, String collection) {
        return new FlexibleCollection<>(sync.getDatabase(database).getCollection(collection), 
            async.getDatabase(database).getCollection(collection));
    }
}
