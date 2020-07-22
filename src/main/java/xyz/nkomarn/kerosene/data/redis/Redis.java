package xyz.nkomarn.kerosene.data.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.nkomarn.kerosene.data.redis.pubsub.PubSubHandler;

/**
 * Database class which allows for easy pooled access
 * to Redis database connections using Jedis.
 */
public class Redis {

    private static JedisPool POOL;
    private static final PubSubHandler HANDLER = new PubSubHandler();

    /**
     * Connects the pool to the remote database using provided credentials.
     *
     * @param host           The address of the Redis database.
     * @param port           The port of the Redis database.
     * @param password       The password of the Redis database.
     * @param maxConnections The maximum amount of connections in the pool.
     * @param timeout        The timeout for an individual connection.
     * @return Whether a connection has been successfully established.
     */
    public static boolean connect(String host, int port, String password, int maxConnections, int timeout) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxConnections);
        POOL = new JedisPool(config, host, port, timeout, password);
        return true;
    }

    /**
     * Closes the Redis connection pool.
     */
    public static void close() {
        if (!POOL.isClosed()) {
            POOL.close();
        }
    }

    /**
     * Returns the connection pool which can be used to fetch a Jedis instance.
     *
     * @return The Redis connection pool.
     */
    public static JedisPool getPool() {
        return POOL;
    }

    /**
     * Returns a Jedis instance from the pool.
     *
     * @return A Jedis instance (resource);
     */
    public static Jedis getResource() {
        return POOL.getResource();
    }

    /**
     * Subscribes to the given Pub/Sub channels.
     *
     * @param channels The channel(s) to subscribe to.
     */
    public static void subscribe(String... channels) {
        HANDLER.subscribe(channels);
    }

    /**
     * Unsubscribes from the given Pub/Sub channels.
     *
     * @param channels The channel(s) to unsubscribe from.
     */
    public static void unsubscribe(String... channels) {
        HANDLER.unsubscribe(channels);
    }
}
