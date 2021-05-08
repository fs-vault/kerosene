package com.firestartermc.kerosene.command;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import com.firestartermc.kerosene.Kerosene;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class CommandManager {

    private final PaperCommandManager commandManager;

    public CommandManager(@NotNull Kerosene kerosene) {
        commandManager = new PaperCommandManager(kerosene);
        commandManager.enableUnstableAPI("brigadier");

        registerCompletion("visible_players", context -> {
            if (context.getSender() instanceof Player) {
                return Bukkit.getOnlinePlayers().stream()
                        .filter(player -> context.getPlayer().canSee(player))
                        .map(Player::getName)
                        .collect(Collectors.toList());
            }

            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        });
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
