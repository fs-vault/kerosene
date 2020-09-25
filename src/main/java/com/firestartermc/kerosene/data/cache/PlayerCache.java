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

    private final Map<UUID, Map<K, V>> data;

    /**
     * Create an instance of the cache.
     */
    public PlayerCache() {
        this.data = new HashMap<>();
    }

    /**
     * Returns an Optional of the value of a key stored in the cache.
     *
     * @param uuid The UUID of the player for which to return the value.
     * @param key  The key of the value to look up.
     * @return An Optional of the value stored.
     */
    public Optional<V> getCached(UUID uuid, K key) {
        Map<K, V> playerData = data.get(uuid);

        if (playerData == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(playerData.get(key));
    }

    /**
     * Returns an Optional of the value of a key stored in the cache.
     * If not cached, a callback will be called to fetch the value.
     *
     * @param uuid     The UUID of the player.
     * @param key      The key of the value to look up.
     * @param callable The callable to call when the key is not present in the cache.
     * @return value An Optional of the stored or callback value.
     */
    public CompletableFuture<V> get(UUID uuid, K key, Callable<? extends V> callable) {
        Map<K, V> playerData = data.get(uuid);

        if (playerData == null) {
            data.put(uuid, new HashMap<>());
            playerData = data.get(uuid);
        }

        if (playerData.containsKey(key)) {
            return CompletableFuture.completedFuture(playerData.get(key));
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                V value = callable.call();
                put(uuid, key, value);
                return value;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Invalidate a specific key for a player.
     *
     * @param uuid The UUID of the player.
     * @param key  The key to invalidate.
     */
    public void invalidate(UUID uuid, K key) {
        Map<K, V> playerData = data.get(uuid);

        if (playerData != null) {
            playerData.remove(key);
        }
    }

    /**
     * Invalidate all keys linked to a player.
     *
     * @param uuid The UUID of the player.
     */
    public void invalidateAll(UUID uuid) {
        data.remove(uuid);
    }

    /**
     * Cache a specific key-value pair.
     *
     * @param uuid  The UUID of the player.
     * @param key   The key of the value to cache.
     * @param value The value to cache.
     */
    public void put(UUID uuid, K key, V value) {
        Map<K, V> playerData = data.get(uuid);

        if (playerData == null) {
            data.put(uuid, new HashMap<>());
            playerData = data.get(uuid);
        }

        playerData.put(key, value);
    }

}
