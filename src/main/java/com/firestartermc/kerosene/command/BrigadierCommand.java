package com.firestartermc.kerosene.command;

import com.firestartermc.kerosene.Kerosene;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class BrigadierCommand implements CommandExecutor {

    public void registerCompletions() {
        var commodore = Kerosene.getKerosene().getCommodore();
        getCompletions().forEach(commodore::register);
    }

    @NotNull
    public List<LiteralArgumentBuilder<?>> getCompletions() {
        return Collections.emptyList();
    }
}
