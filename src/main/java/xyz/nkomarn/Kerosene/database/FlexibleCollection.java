package xyz.nkomarn.Kerosene.database;

import com.mongodb.client.MongoCollection;

/**
 * Represents a flexible sync/async Mongo collection.
 * The type of collection can be chosen using sync() and async().
 */
public class FlexibleCollection<T> {
    private MongoCollection<T> sync;
    private com.mongodb.reactivestreams.client.MongoCollection<T> async;

    public FlexibleCollection(MongoCollection<T> sync, com.mongodb.reactivestreams.client.MongoCollection<T> async) {
        this.sync = sync;
        this.async = async;
    }

    public MongoCollection<T> sync() {
        return sync;
    }

    public com.mongodb.reactivestreams.client.MongoCollection<T> async() {
        return async;
    }
}
