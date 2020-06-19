package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionBuilder extends ItemBuilderBase<PotionBuilder> {

    public PotionBuilder() {
        super(Material.POTION, 1);
    }

    public PotionBuilder splash() {
        this.item.setType(Material.SPLASH_POTION);
        return this;
    }

    public PotionBuilder tippedArrow() {
        this.item.setType(Material.TIPPED_ARROW);
        return this;
    }

    public PotionBuilder base(PotionType type) {
        return base(new PotionData(type));
    }

    public PotionBuilder base(PotionType type, boolean extended, boolean upgraded) {
        return base(new PotionData(type, extended, upgraded));
    }

    public PotionBuilder base(PotionData potionData) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.setBasePotionData(potionData);
        this.item.setItemMeta(meta);
        return this;
    }

    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
    }

    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles));
    }

    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        return effect(new PotionEffect(type, duration, amplifier, ambient));
    }

    public PotionBuilder effect(PotionEffectType type, int duration, int amplifier) {
        return effect(new PotionEffect(type, duration, amplifier));
    }

    public PotionBuilder effect(PotionEffect potionEffect) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.addCustomEffect(potionEffect, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public PotionBuilder color(int r, int g, int b) {
        return color(Color.fromRGB(r, g, b));
    }

    public PotionBuilder color(Color color) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.setColor(color);
        this.item.setItemMeta(meta);
        return this;
    }
}
