package com.firestartermc.kerosene.command;

import co.aikar.commands.BaseCommand;
import com.firestartermc.kerosene.Kerosene;
import org.jetbrains.annotations.NotNull;

public class Command extends BaseCommand {

    @NotNull
    protected CommandManager getCommandManager() {
        return Kerosene.getKerosene().getCommandManager();
    }
}
