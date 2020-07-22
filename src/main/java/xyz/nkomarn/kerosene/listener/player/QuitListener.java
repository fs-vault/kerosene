package xyz.nkomarn.kerosene.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import xyz.nkomarn.kerosene.data.Cooldown;
import xyz.nkomarn.kerosene.util.internal.Debug;
import xyz.nkomarn.kerosene.data.Toggle;

/**
 * Listener for cleaning up player data when a player leaves the server.
 */
public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Toggle.CACHE.invalidateAll(event.getPlayer().getUniqueId());
        Cooldown.CACHE.invalidateAll(event.getPlayer().getUniqueId());
        Debug.disableAll(event.getPlayer());
    }
}
