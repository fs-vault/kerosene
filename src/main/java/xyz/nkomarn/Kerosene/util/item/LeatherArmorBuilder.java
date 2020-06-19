package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class LeatherArmorBuilder extends ItemBuilderBase<LeatherArmorBuilder> {

    public LeatherArmorBuilder(Type type) {
        this(type, 1);
    }

    public LeatherArmorBuilder(Type type, int amount) {
        super(type.getMaterial(), amount);
    }

    public LeatherArmorBuilder type(Type type) {
        this.item.setType(type.getMaterial());
        return this;
    }

    public LeatherArmorBuilder color(int r, int g, int b) {
        return color(Color.fromRGB(r, g, b));
    }

    public LeatherArmorBuilder color(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    public enum Type {
        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_HELMET),
        LEGGINGS(Material.LEATHER_HELMET),
        BOOTS(Material.LEATHER_HELMET);

        Material material;

        Type(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }
    }

}
