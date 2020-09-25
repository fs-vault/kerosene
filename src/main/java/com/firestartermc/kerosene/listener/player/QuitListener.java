package com.firestartermc.kerosene.listener.player;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.cache.CooldownCache;
import com.firestartermc.kerosene.event.PubSubMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import com.firestartermc.kerosene.util.internal.Debug;
import com.firestartermc.kerosene.data.type.Toggle;

/**
 * Listener for cleaning up player data when a player leaves the server.
 */
public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Toggle.CACHE.invalidateAll(event.getPlayer().getUniqueId());
        Debug.disableAll(event.getPlayer());
    }

}
