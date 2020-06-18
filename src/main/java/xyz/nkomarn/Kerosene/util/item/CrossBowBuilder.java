package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

public class CrossBowBuilder extends ItemBuilderBase<CrossBowBuilder> {

    public CrossBowBuilder() {
        this(1);
    }

    public CrossBowBuilder(int amount) {
        super(Material.CROSSBOW, amount);
    }

    public CrossBowBuilder chargeProjectile(ItemStack projectile) {
        if (isValidProjectile(projectile.getType())) {
            CrossbowMeta meta = (CrossbowMeta) this.item.getItemMeta();
            meta.addChargedProjectile(projectile);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    private static boolean isValidProjectile(Material material) {
        return material == Material.ARROW
                || material == Material.SPECTRAL_ARROW
                || material == Material.TIPPED_ARROW
                || material == Material.FIREWORK_ROCKET;
    }

}
