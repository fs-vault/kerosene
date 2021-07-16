package com.firestartermc.kerosene.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface BrigadierCommand extends CommandExecutor {

    @NotNull
    default List<LiteralArgumentBuilder<?>> getCompletions() {
        return Collections.emptyList();
    }
}
