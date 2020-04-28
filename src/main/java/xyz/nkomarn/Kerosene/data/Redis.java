package xyz.nkomarn.Kerosene.data;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.nkomarn.Kerosene.data.pubsub.PubSubHandler;

/**
 * Database class which allows for easy pooled access
 * to Redis database connections using Jedis.
 */
public class Redis {
    private static JedisPool pool;
    private static final PubSubHandler pubSubHandler = new PubSubHandler();

    /**
     * Connects the pool to the remote database using provided credentials.
     * @param host The address of the Redis database.
     * @param port The port of the Redis database.
     * @param password The password of the Redis database.
     * @param maxConnections The maximum amount of connections in the pool.
     * @param timeout The timeout for an individual connection.
     * @return Whether a connection has been successfully established.
     */
    public static boolean connect(final String host, final int port, final String password, final int maxConnections,
                           final int timeout) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxConnections);
        pool = new JedisPool(config, host, port, timeout, password);
        return true;
    }

    /**
     * Closes the Redis connection pool.
     */
    public static void close() {
        if (!pool.isClosed()) pool.close();
    }

    /**
     * Returns the connection pool which can be used to fetch a Jedis instance.
     * @return The Redis connection pool.
     */
    public static JedisPool getPool() {
        return pool;
    }

    /**
     * Subscribes to the given Pub/Sub channels.
     * @param channels The channel(s) to subscribe to.
     */
    public static void subscribe(final String... channels) {
        pubSubHandler.subscribe(channels);
    }

    /**
     * Unsubscribes from the given Pub/Sub channels.
     * @param channels The channel(s) to unsubscribe from.
     */
    public static void unsubscribe(final String... channels) {
        pubSubHandler.unsubscribe(channels);
    }
}
