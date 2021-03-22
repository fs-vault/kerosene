package com.firestartermc.kerosene.gui;


import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;

public class PlayerGui extends Gui {

    private final Player player;

    public PlayerGui(Player player, String title, int rows) {
        this(player, null, title, rows);
    }

    public PlayerGui(Player player, Gui parent, String title, int rows) {
        super(parent, title, rows);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Set<Player> getViewers() {
        return Collections.singleton(this.player);
    }

    public void open() {
        open(this.player);
    }

    /**
     * Please use {@link PlayerGui#open()} when dealing with PlayerGui.
     */
    @Deprecated
    @Override
    public void open(Player... players) {
        super.open(players);
    }

    /**
     * Please use {@link PlayerGui#open()} when dealing with PlayerGui.
     */
    @Deprecated
    @Override
    public void open(Set<Player> players) {
        super.open(players);
    }
}

