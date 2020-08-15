package com.firestartermc.kerosene.util.item;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemUtils {

    private ItemUtils() {
    }

    public static ItemStack makeFillItem(Material fillMaterial) {
        return new ItemBuilder(fillMaterial)
                .name(" ")
                .addAllItemFlags()
                .build();
    }

    public static @NotNull String getFriendlyName(Material material) {
        return CraftItemStack.asNMSCopy(new ItemStack(material)).getName().getString();
    }
}
