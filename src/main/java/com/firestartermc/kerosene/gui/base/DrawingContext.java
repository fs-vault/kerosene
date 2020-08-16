package com.firestartermc.kerosene.gui.base;

import com.firestartermc.kerosene.gui.Gui;
import org.bukkit.entity.Player;

public final class DrawingContext {

    private final Player player;
    private final Gui gui;

    public DrawingContext(Gui gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public Gui getGui() {
        return gui;
    }

    public Player getPlayer() {
        return player;
    }
}

