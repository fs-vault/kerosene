package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class intended to allow fast building of ItemStacks.
 */
public class ItemBuilder extends ItemBuilderBase<ItemBuilder> {

    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemBuilder(ItemStack item) {
        super(item);
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

}
