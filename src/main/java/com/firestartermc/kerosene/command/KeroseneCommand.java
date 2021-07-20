package com.firestartermc.kerosene.command;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import com.firestartermc.kerosene.Kerosene;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KeroseneCommand implements AnnotatedCommand {

    private final Kerosene kerosene;

    public KeroseneCommand(@NotNull Kerosene kerosene) {
        this.kerosene = kerosene;
    }

    @CommandMethod("kerosene")
    @CommandDescription("Kerosene version command.")
    private void root(CommandSender sender) {
        var version = kerosene.getDescription().getVersion();
        sender.sendMessage(Component.text("Running Kerosene version " + version + "."));
    }
}
