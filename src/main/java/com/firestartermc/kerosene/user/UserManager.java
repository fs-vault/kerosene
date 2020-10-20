package com.firestartermc.kerosene.user;

import com.firestartermc.kerosene.Kerosene;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager implements Listener {

    private final Kerosene kerosene;
    private final Map<UUID, User> users;

    public UserManager(Kerosene kerosene) {
        this.kerosene = kerosene;
        this.users = new HashMap<>();
    }

    @Nullable
    public User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    @Nullable
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        users.put(event.getPlayer().getUniqueId(), new User(kerosene, event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        users.remove(event.getPlayer().getUniqueId());
    }
}
