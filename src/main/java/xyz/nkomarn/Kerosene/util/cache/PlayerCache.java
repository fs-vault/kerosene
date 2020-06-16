package xyz.nkomarn.Kerosene.util.cache;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class PlayerCache<K, V> {

    private final Map<UUID, Map<K, V>> data;

    public PlayerCache() {
        data = new HashMap<>();
    }

    public V getIfPresent(UUID uniqueId, K key) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) return null;
        return playerData.get(key);
    }

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

    public void invalidate(UUID uniqueId, K key) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) return;
        playerData.remove(key);
    }

    public void invalidateAll(UUID uniqueId) {
        this.data.remove(uniqueId);
    }

    public void put(UUID uniqueId, K key, V value) {
        Map<K, V> playerData = data.get(uniqueId);
        if (playerData == null) {
            this.data.put(uniqueId, new HashMap<>());
            playerData = this.data.get(uniqueId);
        }

        playerData.put(key, value);
    }
}
