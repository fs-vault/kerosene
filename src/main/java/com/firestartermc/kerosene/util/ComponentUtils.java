package com.firestartermc.kerosene.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ComponentUtils {

    @NotNull
    public static Component getItemAsComponent(@NotNull ItemStack item) {
        var factory = Bukkit.getItemFactory();

        return factory.displayName(item)
                .hoverEvent(factory.asHoverEvent(item, showItem -> showItem));
    }
}
