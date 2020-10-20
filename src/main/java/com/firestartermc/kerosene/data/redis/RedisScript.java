package com.firestartermc.kerosene.data.redis;

import com.firestartermc.kerosene.Kerosene;
import io.lettuce.core.ScriptOutputType;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

public class RedisScript {

    private final String script;
    private final String hash;

    RedisScript(@NotNull String script, @NotNull String hash) {
        this.script = script;
        this.hash = hash;
    }

    @NotNull
    public <T> Flux<T> evalCast() {
        return this.eval().map(o -> (T) o);
    }

    @NotNull
    public <T> Flux<T> evalCast(@NotNull String[] keys, @NotNull String[] args) {
        return this.eval(keys, args).map(o -> (T) o);
    }

    @NotNull
    public Flux<Object> eval() {
        return this.eval(new String[0], new String[0]);
    }

    @NotNull
    public Flux<Object> eval(@NotNull String[] keys, @NotNull String[] args) {
        return Kerosene.getKerosene().getRedis().reactive()
                .evalsha(this.hash, ScriptOutputType.MULTI, keys, args)
                .onErrorResume((one) ->
                    Kerosene.getKerosene().getRedis().reactive()
                            .eval(this.script, ScriptOutputType.MULTI, keys, args)
                );
    }
}
