package xyz.nkomarn.Kerosene.database.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import xyz.nkomarn.Kerosene.velocity.Kerosene;

public class RedisDatabase {
    private static JedisPool pool;

    public static void connect(final String host, final int port, final String password, final int maxConnections) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Math.max(8, maxConnections));
        pool = new JedisPool(poolConfig, host, port, 0, password);
        try (final Jedis jedis = pool.getResource()) {
            Kerosene.getLogger().info("Redis connected.");
        } catch (JedisConnectionException connectionException) {
            Kerosene.getLogger().error("Failed to connect to Redis.", connectionException);
        }
    }

    public static JedisPool getPool() {
        return pool;
    }

    public static Jedis getResource() {
        return pool.getResource();
    }

    public static void disconnect() {
        pool.close();
    }
}
