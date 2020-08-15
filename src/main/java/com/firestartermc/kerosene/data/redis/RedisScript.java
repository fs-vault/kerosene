package com.firestartermc.kerosene.data.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.ArrayList;
import java.util.List;

public class RedisScript {

    public static RedisScript of(String script) {
        try {
            try (Jedis jedis = Redis.getResource()) {
                String hash = jedis.scriptLoad(script);
                return new RedisScript(script, hash);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final String script;
    private final String hash;

    private RedisScript(String script, String hash) {
        this.script = script;
        this.hash = hash;
    }

    public <T> T evalCast() {
        return (T) this.eval();
    }

    public <T> T evalCast(List<String> keys, List<String> args) {
        return (T) this.eval(keys, args);
    }

    public Object eval() {
        return this.eval(new ArrayList<>(), new ArrayList<>());
    }

    public Object eval(List<String> keys, List<String> args) {
        try (Jedis jedis = Redis.getResource()) {
            try {
                return jedis.evalsha(this.hash, keys, args);
            } catch (JedisNoScriptException e) {
                return jedis.eval(this.script, keys, args);
            }
        }
    }

}
