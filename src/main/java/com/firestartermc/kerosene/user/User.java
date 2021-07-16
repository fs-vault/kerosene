package com.firestartermc.kerosene.user;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.cache.CooldownCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class User {

    private final Player player;
    private final CooldownCache cooldownCache;

    public User(Kerosene kerosene, Player player) {
        this.player = player;
        this.cooldownCache = new CooldownCache(kerosene, player.getUniqueId());
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public CooldownCache getCooldowns() {
        return cooldownCache;
    }
}
