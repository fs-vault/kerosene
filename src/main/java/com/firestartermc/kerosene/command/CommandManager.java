package com.firestartermc.kerosene.command;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import com.firestartermc.kerosene.Kerosene;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private final PaperCommandManager commandManager;

    public CommandManager(@NotNull Kerosene kerosene) {
        commandManager = new PaperCommandManager(kerosene);
        commandManager.enableUnstableAPI("brigadier");
    }

    @NotNull
    public PaperCommandManager getRawManager() {
        return commandManager;
    }

    public void registerCommands(@NotNull Command... commands) {
        for (var command : commands) {
            getRawManager().registerCommand(command, true);
        }
    }

    public void unregisterCommands(@NotNull Command... commands) {
        for (var command : commands) {
            getRawManager().unregisterCommand(command);
        }
    }

    public void registerCompletion(@NotNull String id, @NotNull CommandCompletions.AsyncCommandCompletionHandler<BukkitCommandCompletionContext> handler) {
        getRawManager().getCommandCompletions().registerCompletion(id, handler);
    }
}
