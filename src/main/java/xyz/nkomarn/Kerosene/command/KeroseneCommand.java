package xyz.nkomarn.Kerosene.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KeroseneCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        final String prefix = "&6&lKerosene: &7";

        if (args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    String.format("%sFirestarter core by TechToolbox.")));
        }
        return true;
    }
}
