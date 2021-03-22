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
 * Database class which allows for easy pooled access to Redis database connections.
 */
public class Redis {

    private final StatefulRedisConnection<String, String> connection;
    private final RedisPubSubAsyncCommands<String, String> pubSub;

    public Redis(@NotNull String uri) {
        var redisUri = RedisURI.create(uri);
        var client = RedisClient.create(redisUri);

        this.connection = client.connect();
        this.pubSub = client.connectPubSub().async();
        this.pubSub.getStatefulConnection().addListener(new PubSubHandler());
    }

    @NotNull
    public RedisCommands<String, String> sync() {
        return connection.sync();
    }

    @NotNull
    public RedisAsyncCommands<String, String> async() {
        return connection.async();
    }

    @NotNull
    public RedisReactiveCommands<String, String> reactive() {
        return connection.reactive();
    }

    public void subscribe(@NotNull String... channels) {
        pubSub.subscribe(channels);
    }

    public void unsubscribe(@NotNull String... channels) {
        pubSub.unsubscribe(channels);
    }

    @NotNull
    public Mono<RedisScript> loadScript(@NotNull String script) {
        return reactive().scriptLoad(script)
                .map(hash -> new RedisScript(script, hash));
    }

    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}
