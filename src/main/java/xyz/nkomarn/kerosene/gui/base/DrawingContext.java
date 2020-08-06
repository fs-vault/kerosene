package xyz.nkomarn.kerosene.gui.base;

import org.bukkit.entity.Player;
import xyz.nkomarn.kerosene.gui.Gui;

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
