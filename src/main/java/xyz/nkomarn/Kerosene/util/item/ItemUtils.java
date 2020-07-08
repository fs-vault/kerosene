package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public final class ItemUtils {

    private ItemUtils() {
    }

    public static ItemStack makeFillItem(Material fillMaterial) {
        return new ItemBuilder(fillMaterial)
                .name(" ")
                .addAllItemFlags()
                .build();
    }

}
