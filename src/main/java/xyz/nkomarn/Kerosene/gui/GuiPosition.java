package xyz.nkomarn.Kerosene.gui;

import java.util.Objects;

/**
 * An x,y based position that reflect the position on a {@link Gui}.
 */
public class GuiPosition {

    private int x, y;

    /**
     * Create a new position.
     *
     * @param x The x.
     * @param y The y.
     */
    public GuiPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Gets the x index.
     * @return The x index.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x index.
     * @param x The x index.
     */
    public void setX(int x) {
        if (x < 0) {
            x = 0;
        }
        if (x > 8) {
            x = 8;
        }
        this.x = x;
    }

    /**
     * Gets the y index.
     * @return The y index.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y index.
     * @param y The y index.
     */
    public void setY(int y) {
        if (y < 0) {
            y = 0;
        }
        this.y = y;
    }

    /**
     * Get the bukkit inventory slot index
     * @return The slot index.
     */
    public int toSlot() {
        return (y * 9) + x;
    }

    /**
     * Get a GuiPosition from a bukkit inventory slot index.
     * @param slot The index to convert.
     * @return The GuiPosition.
     */
    public static GuiPosition fromSlot(int slot) {
        return new GuiPosition(slot % 9, slot / 9);
    }

    @Override
    public String toString() {
        return "GuiPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiPosition guiPosition = (GuiPosition) o;
        return x == guiPosition.x &&
                y == guiPosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
