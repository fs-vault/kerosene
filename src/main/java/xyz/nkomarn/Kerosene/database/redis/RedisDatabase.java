package xyz.nkomarn.Kerosene.database.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.nkomarn.Kerosene.velocity.Kerosene;

public class RedisDatabase {
    private static JedisPool pool;

    public static void connect(final String host, final int port, final String password, final int maxConnections) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Math.max(8, maxConnections));
        pool = new JedisPool(poolConfig, host, port, 0, password);
        Kerosene.getLogger().info("Redis connected.");
    }

    public static JedisPool getPool() {
        return pool;
    }
}
