package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

/**
 * Utility class intended to allow fast building of CrossBows.
 */
public class CrossBowBuilder extends ItemBuilderBase<CrossBowBuilder> {

    /**
     * A single crossbow
     */
    public CrossBowBuilder() {
        super(Material.CROSSBOW, 1);
    }

    /**
     * Add a projectile to the current charged projectiles.
     * @param projectile The projectile to add
     * @return This instance
     */
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
