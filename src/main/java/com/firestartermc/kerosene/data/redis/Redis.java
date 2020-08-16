package com.firestartermc.kerosene.data.redis;

import com.firestartermc.kerosene.data.redis.pubsub.PubSubHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Database class which allows for easy pooled access to Redis database connections using Jedis.
 */
public class Redis {

    private final RedisClient client;
    private final StatefulRedisConnection<String, String> connection;
    private final RedisPubSubAsyncCommands<String, String> pubSub;

    public Redis(@NotNull String uri) {
        RedisURI redisUri = RedisURI.create(uri);

        this.client = RedisClient.create(redisUri);
        this.connection = client.connect();
        this.pubSub = client.connectPubSub().async();
        this.pubSub.getStatefulConnection().addListener(new PubSubHandler());
    }

    public @NotNull RedisCommands<String, String> sync() {
        return connection.sync();
    }

    public @NotNull RedisAsyncCommands<String, String> async() {
        return connection.async();
    }

    public @NotNull RedisReactiveCommands<String, String> reactive() {
        return connection.reactive();
    }

    public void subscribe(@NotNull String... channels) {
        pubSub.subscribe(channels);
    }

    public void unsubscribe(@NotNull String... channels) {
        pubSub.unsubscribe(channels);
    }

    public @NotNull Mono<RedisScript> loadScript(@NotNull String script) {
        return reactive().scriptLoad(script)
                .map(hash -> new RedisScript(script, hash));
    }

    public void shutdown() {
        if (connection == null) {
            return;
        }

        connection.close();
    }
}
