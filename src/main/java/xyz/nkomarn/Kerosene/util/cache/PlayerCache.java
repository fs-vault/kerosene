package xyz.nkomarn.Kerosene.util.cache;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Cache for caching multiple key value pairs linked to a player.
 * @param <K> Key
 * @param <V> Value
 */
public class PlayerCache<K, V> {

    private final Map<UUID, Map<K, V>> data;

    /**
     * Default constructor
     */
    public PlayerCache() {
        data = new HashMap<>();
    }

    /**
     * Gets the value of a key if present in the cache. If the key is not present null will be returned.
     * @param uniqueId of the player
     * @param key to lookup
     * @return value if key is present or null.
     */
    public V getIfPresent(UUID uniqueId, K key) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) return null;
        return playerData.get(key);
    }

    /**
     * Gets the value of a key if present in the cache. If the key is not present a callback will be called to obtain the value.
     * @param uniqueId of tke player
     * @param key to lookup
     * @param callable to call when the key is not present in the cahe
     * @return value if key is present or object returned by the callback.
     */
    public V get(UUID uniqueId, K key, Callable<? extends V> callable) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) {
            this.data.put(uniqueId, new HashMap<>());
            playerData = this.data.get(uniqueId);
        }

        if (playerData.containsKey(key)) {
            return playerData.get(key);
        }

        try {
            V value = callable.call();
            put(uniqueId, key, value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Invalidate a specific key for a player.
     * @param uniqueId of the player
     * @param key to invalidate.
     */
    public void invalidate(UUID uniqueId, K key) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) return;
        playerData.remove(key);
    }

    /**
     * Invalidate all keys linked to a player.
     * @param uniqueId of the player
     */
    public void invalidateAll(UUID uniqueId) {
        this.data.remove(uniqueId);
    }

    /**
     * Cache a specific key value pair.
     * @param uniqueId of the player
     * @param key to cache
     * @param value to cache
     */
    public void put(UUID uniqueId, K key, V value) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) {
            this.data.put(uniqueId, new HashMap<>());
            playerData = this.data.get(uniqueId);
        }

        playerData.put(key, value);
    }
}
