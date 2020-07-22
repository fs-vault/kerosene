package xyz.nkomarn.kerosene.util.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Utility class intended to allow fast building of LeatherArmor.
 */
public class LeatherArmorBuilder extends ItemBuilderBase<LeatherArmorBuilder> {

    /**
     * Single leather armor piece.
     * @param type The type of the leather armor piece
     */
    public LeatherArmorBuilder(LeatherArmorType type) {
        super(type.getMaterial(), 1);
    }

    /**
     * Set the type of the armor piece.
     * @param type The type of leather armor piece
     * @return The instance
     */
    public LeatherArmorBuilder type(LeatherArmorType type) {
        this.item.setType(type.getMaterial());
        return this;
    }

    /**
     * Set the color of the leather armor piece.
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return This instance
     */
    public LeatherArmorBuilder color(byte r, byte g, byte b) {
        return color(Color.fromRGB(r, g, b));
    }

    /**
     * Set the color of the leather armor piece
     * @param color The color
     * @return This instance
     */
    public LeatherArmorBuilder color(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Types of leather armor pieces.
     */
    public enum LeatherArmorType {
        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_HELMET),
        LEGGINGS(Material.LEATHER_HELMET),
        BOOTS(Material.LEATHER_HELMET);

        Material material;

        LeatherArmorType(Material material) {
            this.material = material;
        }

        /**
         * Returns the Material of the leather armour piece.
         * @return The material
         */
        public Material getMaterial() {
            return material;
        }
    }

}
