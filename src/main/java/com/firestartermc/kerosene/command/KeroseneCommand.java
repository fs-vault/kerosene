package com.firestartermc.kerosene.command;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.util.Constants;
import com.firestartermc.kerosene.util.MessageUtils;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import com.firestartermc.kerosene.util.internal.Debug;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class KeroseneCommand implements TabExecutor {

    private final Kerosene kerosene;
    private final String messagePrefix;

    public KeroseneCommand(@NotNull Kerosene kerosene) {
        this.kerosene = kerosene;
        this.messagePrefix = MessageUtils.formatColors("&6&lKerosene: &e", false);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length < 1 || !sender.hasPermission("firestarter.admin")) {
            sender.sendMessage(messagePrefix + "Firestarter core server component (version " + kerosene.getDescription().getVersion() + ").");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "debug" -> handleDebug(sender, args);
            case "health" -> handleHealth(sender);
            default -> sender.sendMessage(Constants.ERROR_PREFIX + "Invalid operation.");
        }
        return true;
    }

    public void handleDebug(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            var cat = args[1];
            var state = Debug.toggleDebug(cat, sender);

            if (state.isEmpty()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c&lError: &7Invalid category specified."));
                return;
            }

            var stateText = state.get() ? "enabled" : "disabled";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Debug.PREFIX + String.format("&e%s &7debug messages have been &e%s&7.", cat, stateText)));
        } else {
            sender.sendMessage(Debug.PREFIX + "Use '/debug <category>' to toggle a debug category");
        }
    }

    public void handleHealth(@NotNull CommandSender sender) {
        var sqlFuture = CompletableFuture.supplyAsync(() -> {
            var playerdata = kerosene.getPlayerData();
            if (playerdata == null) {
                return "Not connected.";
            }

            try {
                long start = System.currentTimeMillis();
                var connection = playerdata.getConnection();
                var statement = connection.prepareStatement("SELECT 1");

                try (connection; statement) {
                    statement.execute();
                }

                return System.currentTimeMillis() - start + " ms.";
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        });

        var redisFuture = CompletableFuture.supplyAsync(() -> {
            var redis = kerosene.getRedis();
            if (redis == null) {
                return "Not connected.";
            }

            return redis.sync().ping() + " ms.";
        });

        sender.sendMessage(messagePrefix + "Running system health check.");

        sqlFuture.thenAccept(ping -> {
            sender.sendMessage(ChatColor.YELLOW + "SQL ping: " + ping);
        }).exceptionally(throwable -> {
            sender.sendMessage(ChatColor.RED + "Failed to ping SQL.");
            return null;
        });

        redisFuture.thenAccept(ping -> {
            sender.sendMessage(ChatColor.YELLOW + "Redis ping: " + ping);
        }).exceptionally(throwable -> {
            sender.sendMessage(ChatColor.RED + "Failed to ping Redis.");
            return null;
        });
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return ImmutableList.of("debug", "health");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("debug")) {
                return new ArrayList<>(Debug.getCategories());
            }
        }

        return Collections.emptyList();
    }
}
