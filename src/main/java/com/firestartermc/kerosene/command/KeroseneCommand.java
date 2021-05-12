package com.firestartermc.kerosene.command;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.util.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.firestartermc.kerosene.util.internal.Debug;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@CommandAlias("kerosene")
public class KeroseneCommand extends Command {

    private final Kerosene kerosene;

    public KeroseneCommand(@NotNull Kerosene kerosene) {
        this.kerosene = kerosene;
        getCommandManager().registerCompletion("debug_categories", context -> Debug.getCategories());
    }

    @Default
    public void base(CommandSender sender) {
        sender.sendMessage(Component.text("Running Kerosene version " + kerosene.getDescription().getVersion() + "."));
    }

    @Subcommand("health")
    @CommandPermission("firestarter.admin")
    public void onHealth(CommandSender sender) {
        var sqlFuture = CompletableFuture.supplyAsync(() -> {
            var playerdata = kerosene.getDatabase();

            try {
                long start = System.currentTimeMillis();
                var connection = playerdata.getConnection();
                var statement = connection.prepareStatement("SELECT 1;");

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

        sqlFuture.thenAccept(ping -> {
            sender.sendMessage(ChatColor.WHITE + "SQL ping: " + ChatColor.GREEN + ping);
        }).exceptionally(throwable -> {
            sender.sendMessage(ChatColor.RED + "Failed to ping SQL.");
            return null;
        });

        redisFuture.thenAccept(ping -> {
            sender.sendMessage(ChatColor.WHITE + "Redis ping: " + ChatColor.GREEN + ping);
        }).exceptionally(throwable -> {
            sender.sendMessage(ChatColor.RED + "Failed to ping Redis.");
            return null;
        });
    }

    @Subcommand("debug")
    @CommandPermission("firestarter.admin")
    @CommandCompletion("@debug_categories")
    @Syntax("<category>")
    public void handleDebug(CommandSender sender, @Single String category) {
        var state = Debug.toggleDebug(category, sender);

        if (state.isEmpty()) {
            sender.sendMessage(MessageUtils.formatColors("&c&lERROR: &fInvalid category specified."));
            return;
        }

        var stateText = state.get() ? "enabled" : "disabled";
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Debug.PREFIX + String.format("&e%s &7debug messages have been &e%s&7.", category, stateText)));
    }
}
