package com.firestartermc.kerosene.commands;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.data.redis.Redis;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import com.firestartermc.kerosene.util.internal.Debug;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class KeroseneCommand implements TabExecutor {

    private final Kerosene kerosene;


    public KeroseneCommand(@NotNull Kerosene kerosene) {
        this.kerosene = kerosene;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length < 1 || !sender.hasPermission("firestarter.admin")) {
            sender.sendMessage(Kerosene.PREFIX + "Firestarter utility core (version " + kerosene.getDescription().getVersion() + ").");
            return true;
        }

        if (args[0].equalsIgnoreCase("debug")) {
            handleDebug(sender, args);
        } else if (args[0].equalsIgnoreCase("health")) {
            healthCheck(sender, args);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &7Invalid operation specified."));
        }

        return true;
    }

    public void handleDebug(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            String cat = args[1];

            Optional<Boolean> state = Debug.toggleDebug(cat, sender);
            if (!state.isPresent()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c&lError: &7Invalid category specified."));
                return;
            }

            String stateText = state.get() ? "enabled" : "disabled";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Debug.PREFIX + String.format("&e%s &7debug messages have been &e%s&7.", cat, stateText)));
        } else {
            sender.sendMessage(Debug.PREFIX + "Use '/debug <category>' to toggle a debug category");
        }
    }

    public void healthCheck(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(Kerosene.PREFIX + "Running system health check.");
        Kerosene.getRedis().reactive().ping().subscribe(result -> {
            sender.sendMessage(ChatColor.GRAY + "Redis ping: " + ChatColor.GOLD + result);
        });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return ImmutableList.of("debug", "health");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("debug")) {
                return new ArrayList<>(Debug.getCategories());
            }
        }

        return Collections.emptyList();
    }


}
