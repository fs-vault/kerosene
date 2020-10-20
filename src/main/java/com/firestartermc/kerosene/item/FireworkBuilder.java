package com.firestartermc.kerosene.item;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Utility class intended to allow fast building of Fireworks.
 */
public class FireworkBuilder extends ItemBuilderBase<FireworkBuilder> {

    /**
     * Single firework without effects
     */
    public FireworkBuilder() {
        this(1);
    }

    /**
     * Custom amount of fireworks without effects.
     * @param amount The amount of the ItemStack
     */
    public FireworkBuilder(int amount) {
        super(Material.FIREWORK_ROCKET, amount);
    }

    /**
     * Add an effect to the firework.
     * @param builder The FireworkEffect.Builder
     * @return This instance
     */
    public FireworkBuilder effect(FireworkEffect.Builder builder) {
        return effect(builder.build());
    }

    /**
     * Add an effect to the firework.
     * @param effect The FireworkEffect
     * @return This instance
     */
    public FireworkBuilder effect(FireworkEffect effect) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.addEffect(effect);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Add multiple effect to the firework.
     * @param effects The array of effects to add.
     * @return This instance
     */
    public FireworkBuilder effects(FireworkEffect... effects) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.addEffects(effects);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the power of the firework.
     * @param power The power to set for the firework
     * @return This instance
     */
    public FireworkBuilder power(int power) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.setPower(power);
        this.item.setItemMeta(meta);
        return this;
    }

}
