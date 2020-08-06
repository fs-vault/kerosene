package xyz.nkomarn.kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class intended to allow fast building of ItemStacks.
 */
public class ItemBuilder extends ItemBuilderBase<ItemBuilder> {

    /**
     * Single item
     * @param material The material of the ItemStack
     */
    public ItemBuilder(Material material) {
        super(material);
    }

    /**
     * Custom amount of item
     * @param material The material of the ItemStack
     * @param amount The amount of the ItemStack
     */
    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    /**
     * Starting ItemStack
     * @param item The starting ItemStack
     */
    public ItemBuilder(ItemStack item) {
        super(item);
    }

    /**
     * Starting ItemStack
     * @param item The starting ItemStack
     */
    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item.clone());
    }

}
