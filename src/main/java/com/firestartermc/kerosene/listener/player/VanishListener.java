package com.firestartermc.kerosene.listener.player;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.redis.Redis;
import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class VanishListener implements Listener {

    private final Redis redis;

    public VanishListener(Redis redis) {
        this.redis = redis;
    }

    @EventHandler
    public void onPlayerShown(final PlayerShowEvent event) {
        remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerHide(final PlayerHideEvent event) {
        add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Kerosene.getKerosene(), () -> {
            if (event.getPlayer().getMetadata("vanished").stream().anyMatch(MetadataValue::asBoolean)) {
                add(event.getPlayer().getUniqueId());
            }
        }, 20L); // delay is needed, because bungee joined another server, before leaving the current one.
    }

    @EventHandler
    public void onPlayerJoin(final PlayerQuitEvent event) {
        if (event.getPlayer().getMetadata("vanished").stream().anyMatch(MetadataValue::asBoolean)) {
            remove(event.getPlayer().getUniqueId());
        }
    }

    private void add(UUID id) {
        this.redis.reactive().publish("firestarter::vanish-update", "add::" + id.toString()).subscribe();
        this.redis.reactive().sadd("firestarter::vanished", id.toString()).subscribe();
    }

    private void remove(UUID id) {
        this.redis.reactive().publish("firestarter::vanish-update", "remove::" + id.toString()).subscribe();
        this.redis.reactive().srem("firestarter::vanished", id.toString()).subscribe();
    }

}
