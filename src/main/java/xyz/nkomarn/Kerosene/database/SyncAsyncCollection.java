package xyz.nkomarn.Kerosene.database;

import com.mongodb.client.MongoCollection;

public class SyncAsyncCollection<TDocument> {

    private MongoCollection<TDocument> syncCollection;
    private com.mongodb.reactivestreams.client.MongoCollection<TDocument> asyncCollection;

    public SyncAsyncCollection(MongoCollection<TDocument> syncCollection,
                               com.mongodb.reactivestreams.client.MongoCollection<TDocument> asyncCollection) {
        this.syncCollection = syncCollection;
        this.asyncCollection = asyncCollection;
    }

    public MongoCollection<TDocument> sync() {
        return syncCollection;
    }

    public com.mongodb.reactivestreams.client.MongoCollection<TDocument> async() {
        return asyncCollection;
    }

}
