package com.firestartermc.kerosene.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import com.firestartermc.kerosene.util.internal.Debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class KeroseneCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&6&lKerosene: &7A package of Firestarter's basic utilities."));
        } else if (args[0].equalsIgnoreCase("debug")) {
            this.handleDebug(sender, args);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c&lError: &7Invalid operation specified."));
        }
        return true;
    }

    public void handleDebug(CommandSender sender, String[] args) {
        if(args.length == 2) {
            String cat = args[1];

            Optional<Boolean> state = Debug.toggleDebug(cat, sender);
            if (!state.isPresent()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c&lError: &7Invalid category specified."));
                return;
            }

            String stateText = state.get() ? "enabled": "disabled";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Debug.PREFIX + String.format("&e%s &7debug messages have been &e%s&7.", cat, stateText)));
        } else {
            sender.sendMessage(Debug.PREFIX + "Use '/debug <category>' to toggle a debug category");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return ImmutableList.of("debug");
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("debug")) {
                return new ArrayList<>(Debug.getCategories());
            }
        }

        return Collections.emptyList();
    }


}
