package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder extends ItemBuilderBase<FireworkBuilder> {

    public FireworkBuilder() {
        this(1);
    }

    public FireworkBuilder(int amount) {
        super(Material.FIREWORK_ROCKET, amount);
    }

    public FireworkBuilder effect(FireworkEffect.Builder builder) {
        return effect(builder.build());
    }

    public FireworkBuilder effect(FireworkEffect effect) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.addEffect(effect);
        this.item.setItemMeta(meta);
        return this;
    }

    public FireworkBuilder effects(FireworkEffect... effects) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.addEffects(effects);
        this.item.setItemMeta(meta);
        return this;
    }

    public FireworkBuilder power(int power) {
        FireworkMeta meta = (FireworkMeta) this.item.getItemMeta();
        meta.setPower(power);
        this.item.setItemMeta(meta);
        return this;
    }

}
