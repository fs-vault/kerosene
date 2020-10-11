package com.firestartermc.kerosene.user;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.cache.CooldownCache;
import com.firestartermc.kerosene.data.cache.ToggleCache;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class User {

    private final Kerosene kerosene;
    private final Player player;
    private final CooldownCache cooldownCache;
    private final ToggleCache toggleCache;

    public User(Kerosene kerosene, Player player) {
        this.kerosene = kerosene;
        this.player = player;
        this.cooldownCache = new CooldownCache(kerosene, player.getUniqueId());
        this.toggleCache = new ToggleCache(kerosene, player.getUniqueId());
    }

    public Player getPlayer() {
        return player;
    }

    public CooldownCache getCooldowns() {
        return cooldownCache;
    }

    public ToggleCache getToggles() {
        return toggleCache;
    }

    public CompletableFuture<Boolean> teleport(Location location) {
        if (kerosene.getEssentials() == null) {
            return PaperLib.teleportAsync(getPlayer(), location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        kerosene.getEssentials().getUser(getPlayer()).getAsyncTeleport().now(location, false, PlayerTeleportEvent.TeleportCause.PLUGIN, future);
        return future;
    }

}
