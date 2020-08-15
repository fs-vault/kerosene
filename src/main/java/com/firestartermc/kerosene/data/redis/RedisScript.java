package com.firestartermc.kerosene.data.redis;

import com.firestartermc.kerosene.Kerosene;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RedisScript {

    private final String script;
    private final String hash;

    private RedisScript(@NotNull String script, @NotNull String hash) {
        this.script = script;
        this.hash = hash;
    }

    public static CompletableFuture<RedisScript> of(@NotNull String script) {
        CompletableFuture<RedisScript> future = new CompletableFuture<>();
        Kerosene.getRedis().reactive().scriptLoad(script).subscribe(hash -> future.complete(new RedisScript(script, hash)));
        return future;
    }

    public <T> T evalCast() {
        return (T) this.eval();
    }

    public <T> T evalCast(@NotNull List<String> keys, @NotNull List<String> args) {
        return (T) this.eval(keys, args);
    }

    public Object eval() {
        return this.eval(new ArrayList<>(), new ArrayList<>());
    }

    public Object eval(List<String> keys, List<String> args) {
        /*try (Jedis jedis = Redis.getResource()) {
            try {
                return jedis.evalsha(this.hash, keys, args);
            } catch (JedisNoScriptException e) {
                return jedis.eval(this.script, keys, args);
            }
        }*/
        return null;
    }

}
