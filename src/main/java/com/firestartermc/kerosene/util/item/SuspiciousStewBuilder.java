package com.firestartermc.kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Utility class intended to allow fast building of SuspiciousStew.
 */
public class SuspiciousStewBuilder extends ItemBuilderBase<SuspiciousStewBuilder> {

    /**
     * Single stew
     */
    public SuspiciousStewBuilder() {
        this(1);
    }

    /**
     * Multiple stew
     */
    public SuspiciousStewBuilder(int amount) {
        super(Material.SUSPICIOUS_STEW, amount);
    }

    /**
     * Add a custom potion effect to the SuspiciousStem.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @param particles Marks whether the option effect should display particles
     * @param icon Marks whether the option effect should display an icon
     * @return This instance
     */
    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
    }

    /**
     * Add a custom potion effect to the SuspiciousStem.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @param particles Marks whether the option effect should display particles
     * @return This instance
     */
    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles));
    }

    /**
     * Add a custom potion effect to the SuspiciousStem.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @param ambient Marks whether the option effect should display an icon
     * @return This instance
     */
    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        return effect(new PotionEffect(type, duration, amplifier, ambient));
    }

    /**
     * Add a custom potion effect to the SuspiciousStem.
     * @param type The type of the potion effect
     * @param duration The duration of the potion effect in ticks (20 ticks = 1 second)
     * @param amplifier Marks whether the option effect should produce more translucent particles
     * @return This instance
     */
    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier) {
        return effect(new PotionEffect(type, duration, amplifier));
    }

    /**
     * Add a custom potion effect to the SuspiciousStem.
     * @param potionEffect The potion effect to add
     * @return This instance
     */
    public SuspiciousStewBuilder effect(PotionEffect potionEffect) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.addCustomEffect(potionEffect, true);
        this.item.setItemMeta(meta);
        return this;
    }
}
