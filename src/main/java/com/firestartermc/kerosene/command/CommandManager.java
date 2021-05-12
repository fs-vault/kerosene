package com.firestartermc.kerosene.command;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import com.firestartermc.kerosene.Kerosene;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class CommandManager implements Listener {

    private final PaperCommandManager commandManager;
    private final Multimap<Plugin, Command> registeredCommands;

    public CommandManager(@NotNull Kerosene kerosene) {
        commandManager = new PaperCommandManager(kerosene);
        commandManager.enableUnstableAPI("brigadier");
        registeredCommands = ArrayListMultimap.create();

        kerosene.getServer().getPluginManager().registerEvents(this, kerosene);
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
            registeredCommands.put(JavaPlugin.getProvidingPlugin(command.getClass()), command);
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

    @EventHandler
    public void onUnload(PluginDisableEvent event) {
        registeredCommands.get(event.getPlugin()).forEach(this::unregisterCommands);
    }
}
