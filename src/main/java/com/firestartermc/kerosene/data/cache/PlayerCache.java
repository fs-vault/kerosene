package com.firestartermc.kerosene.data.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Key-value pair data cache for persistent data linked to a players.
 *
 * @param <K> Data key type.
 * @param <V> Data value type.
 */
public class PlayerCache<K, V> {

    private final Map<K, V> data;

    /**
     * Create an instance of the cache.
     */
    public PlayerCache() {
        this.data = new HashMap<>();
    }

    /**
     * Returns an Optional of the value of a key stored in the cache.
     *
     * @param key The key of the value to look up.
     * @return An Optional of the value stored.
     */
    public Optional<V> getCached(K key) {
        return Optional.ofNullable(data.get(key));
    }

    /**
     * Returns an Optional of the value of a key stored in the cache.
     * If not cached, a callback will be called to fetch the value.
     *
     * @param key      The key of the value to look up.
     * @param callable The callable to call when the key is not present in the cache.
     * @return value An Optional of the stored or callback value.
     */
    public CompletableFuture<V> get(K key, Callable<? extends V> callable) {
        if (data.containsKey(key)) {
            return CompletableFuture.completedFuture(data.get(key));
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                V value = callable.call();
                put(key, value);
                return value;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Invalidate a specific key for a player.
     *
     * @param key The key to invalidate.
     */
    public void invalidate(K key) {
        data.remove(key);
    }

    /**
     * Invalidate all keys linked to a player.
     */
    public void invalidateAll() {
        data.clear();
    }

    /**
     * Cache a specific key-value pair.
     *
     * @param key   The key of the value to cache.
     * @param value The value to cache.
     */
    public void put(K key, V value) {
        data.put(key, value);
    }

}
