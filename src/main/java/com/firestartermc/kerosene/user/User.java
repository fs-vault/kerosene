package com.firestartermc.kerosene.user;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.cache.CooldownCache;
import com.firestartermc.kerosene.data.cache.ToggleCache;
import org.bukkit.entity.Player;

public class User {

    private final Player player;
    private final CooldownCache cooldownCache;
    private final ToggleCache toggleCache;

    public User(Kerosene kerosene, Player player) {
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
}
