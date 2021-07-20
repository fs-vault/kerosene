package com.firestartermc.kerosene.command;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.command.argument.PlayerArgument;
import io.leangen.geantyref.TypeToken;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CommandBus extends PaperCommandManager<CommandSender> {

    private final AnnotationParser<CommandSender> annotationParser;

    public CommandBus(@NotNull Kerosene kerosene) throws Exception {
        super(kerosene, CommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity());
        this.annotationParser = new AnnotationParser<>(this, CommandSender.class, parameters -> SimpleCommandMeta.empty());

        registerBrigadier();
        registerParsers();
    }

    public void command(@NotNull AnnotatedCommand command) {
        annotationParser.parse(command);
    }

    private void registerParsers() {
        getParserRegistry().registerParserSupplier(TypeToken.get(Player.class), options -> new PlayerArgument.PlayerParser());
    }
}
