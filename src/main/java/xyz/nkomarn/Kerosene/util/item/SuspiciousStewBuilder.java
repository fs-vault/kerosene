package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuspiciousStewBuilder extends ItemBuilderBase<SuspiciousStewBuilder> {

    public SuspiciousStewBuilder() {
        this(1);
    }

    public SuspiciousStewBuilder(int amount) {
        super(Material.SUSPICIOUS_STEW, amount);
    }

    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles, icon));
    }

    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        return effect(new PotionEffect(type, duration, amplifier, ambient, particles));
    }

    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        return effect(new PotionEffect(type, duration, amplifier, ambient));
    }

    public SuspiciousStewBuilder effect(PotionEffectType type, int duration, int amplifier) {
        return effect(new PotionEffect(type, duration, amplifier));
    }

    public SuspiciousStewBuilder effect(PotionEffect potionEffect) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.addCustomEffect(potionEffect, true);
        this.item.setItemMeta(meta);
        return this;
    }
}
