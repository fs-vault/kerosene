package com.firestartermc.kerosene.data.redis;

import com.firestartermc.kerosene.data.redis.pubsub.PubSubHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Database class which allows for easy pooled access to Redis database connections using Jedis.
 */
public class Redis {

    private final RedisClient client;
    private final GenericObjectPool<StatefulRedisConnection<String, String>> pool;
    private final RedisPubSubAsyncCommands<String, String> pubSub;

    public Redis(@NotNull String uri) {
        RedisURI redisUri = RedisURI.create(uri);

        this.client = RedisClient.create(redisUri);
        this.pool = ConnectionPoolSupport.createGenericObjectPool(client::connect, new GenericObjectPoolConfig());
        this.pubSub = client.connectPubSub().async();
        this.pubSub.getStatefulConnection().addListener(new PubSubHandler());
    }

    private StatefulRedisConnection<String, String> borrow() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RedisCommands<String, String> sync() {
        return borrow().sync();
    }

    public RedisAsyncCommands<String, String> async() {
        return borrow().async();
    }

    public RedisReactiveCommands<String, String> reactive() {
        return borrow().reactive();
    }

    public void subscribe(String... channels) {
        pubSub.subscribe(channels);
    }

    public void unsubscribe(String... channels) {
        pubSub.unsubscribe(channels);
    }

    public void shutdown() {
        if (pool == null) {
            return;
        }

        pool.close();
    }
}
