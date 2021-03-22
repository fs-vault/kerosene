package com.firestartermc.kerosene.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.firestartermc.kerosene.util.internal.Debug;

/**
 * Listener for cleaning up player data when a player leaves the server.
 */
public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Debug.disableAll(event.getPlayer());
    }
}
