package com.firestartermc.kerosene.user;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.cache.CooldownCache;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class User extends OfflineUser {

    private final Kerosene kerosene;
    private final Player player;

    private final CooldownCache cooldownCache;

    public User(Kerosene kerosene, Player player) {
        this.kerosene = kerosene;
        this.player = player;
        this.cooldownCache = new CooldownCache(player);
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @NotNull
    public CooldownCache getCooldowns() {
        return cooldownCache;
    }

    @NotNull
    public CompletableFuture<Boolean> teleport(Location location) {
        if (kerosene.getEssentials() == null) {
            return PaperLib.teleportAsync(player, location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        kerosene.getEssentials().getUser(player).getAsyncTeleport().now(location, false, PlayerTeleportEvent.TeleportCause.PLUGIN, future);
        return future;
    }

}
