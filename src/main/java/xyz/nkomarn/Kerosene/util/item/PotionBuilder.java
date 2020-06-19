package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * Utility class intended to allow fast building of Potions and TippedArrows.
 */
public class PotionBuilder extends ItemBuilderBase<PotionBuilder> {

    /**
     * Single potion
     */
    public PotionBuilder() {
        super(Material.POTION, 1);
    }

    /**
     * Make the potion a splash potion.
     * @return This instance
     */
    public PotionBuilder splash() {
        this.item.setType(Material.SPLASH_POTION);
        return this;
    }

    /**
     * Make the potion a tipped arrow.
     * @return This instance
     */
    public PotionBuilder tippedArrow() {
        this.item.setType(Material.TIPPED_ARROW);
        return this;
    }

    /**
     * Set the base potion effect.
     * @param type The type of the potion effect
     * @return This instance
     */
    public PotionBuilder base(PotionType type) {
        return base(new PotionData(type));
    }

    /**
     * Set the base potion effect.
     * @param type The type of the potion effect
     * @param extended Marks whether the option effect should be extended
     * @param upgraded Marks whether the option effect should be upgraded
     * @return This instance
     */
    public PotionBuilder base(PotionType type, boolean extended, boolean upgraded) {
        return base(new PotionData(type, extended, upgraded));
    }

    /**
     * Set the base potion effect.
     * @param potionData PotionData
     * @return This instance
     */
    public PotionBuilder base(PotionData potionData) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.setBasePotionData(potionData);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Add a custom potion effect to the potion.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @param particles Marks whether the option effect should display particles
     * @param icon Marks whether the option effect should display an icon
     * @return This instance
     */
    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
    }

    /**
     * Add a custom potion effect to the potion.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @param particles Marks whether the option effect should display particles
     * @return This instance
     */
    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles));
    }

    /**
     * Add a custom potion effect to the potion.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @return This instance
     */
    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        return effect(new PotionEffect(type, duration, amplifier, ambient));
    }

    /**
     * Add a custom potion effect to the potion.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @return This instance
     */
    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier) {
        return effect(new PotionEffect(type, duration, amplifier));
    }

    /**
     * Add a custom potion effect to the potion.
     * @param potionEffect The potion effect to add
     * @return This instance
     */
    public PotionBuilder effect(PotionEffect potionEffect) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.addCustomEffect(potionEffect, true);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the color of the potion.
     * @param r Red
     * @param g Green
     * @param b Blue
     * @return This instance
     */
    public PotionBuilder color(int r, int g, int b) {
        return color(Color.fromRGB(r, g, b));
    }

    /**
     * Set the color of the potion.
     * @param color The color
     * @return This instance
     */
    public PotionBuilder color(Color color) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }
}
