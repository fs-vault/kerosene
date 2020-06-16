package xyz.nkomarn.Kerosene.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.nkomarn.Kerosene.util.CooldownUtil;
import xyz.nkomarn.Kerosene.util.ToggleUtil;

/**
 * Listener for cleaning up player data when a player leaves the server.
 */
public class PlayerQuitListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event) {
        ToggleUtil.CACHE.invalidateAll(event.getPlayer().getUniqueId());
        CooldownUtil.CACHE.invalidateAll(event.getPlayer().getUniqueId());
    }
}
