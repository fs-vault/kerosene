package com.firestartermc.kerosene.command.argument;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.captions.CaptionVariable;
import cloud.commandframework.captions.StandardCaptionKeys;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import cloud.commandframework.exceptions.parsing.ParserException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PlayerArgument extends CommandArgument<CommandSender, Player> {

    public PlayerArgument(boolean required, @NotNull String name, @NotNull String defaultValue,
                          @Nullable BiFunction<CommandContext<CommandSender>, String, List<String>> suggestionsProvider,
                          @NotNull ArgumentDescription defaultDescription) {
        super(required, name, new PlayerParser(), defaultValue, Player.class, suggestionsProvider, defaultDescription);
    }

    @NotNull
    public static Builder newBuilder(@NotNull String name) {
        return new Builder(name);
    }

    public static final class Builder extends CommandArgument.Builder<CommandSender, Player> {

        protected Builder(@NotNull String name) {
            super(Player.class, name);
        }

        @Override
        @NotNull
        public CommandArgument<CommandSender, Player> build() {
            return new PlayerArgument(isRequired(), getName(), getDefaultValue(), getSuggestionsProvider(), getDefaultDescription());
        }
    }

    public static final class PlayerParser implements ArgumentParser<CommandSender, Player> {

        @Override
        @NotNull
        public ArgumentParseResult<Player> parse(@NotNull CommandContext<CommandSender> context, @NotNull Queue<String> inputQueue) {
            var input = inputQueue.peek();

            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(PlayerArgument.PlayerParser.class, context));
            }

            try {
                var player = Bukkit.getPlayerExact(input);

                if (player == null) {
                    return ArgumentParseResult.failure(new PlayerArgument.PlayerParseException(input, context));
                }

                OUT:
                if (context.getSender() instanceof Player sender) {
                    if (sender.canSee(player)) {
                        break OUT;
                    }

                    return ArgumentParseResult.failure(new PlayerArgument.PlayerParseException(input, context));
                }

                inputQueue.remove();
                return ArgumentParseResult.success(player);
            } catch (final Exception e) {
                return ArgumentParseResult.failure(new PlayerArgument.PlayerParseException(input, context));
            }
        }

        @Override
        @NotNull
        public List<String> suggestions(@NotNull CommandContext<CommandSender> context, @NotNull String input) {
            System.out.println(input);

            if (!(context.getSender() instanceof Player sender)) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.startsWith(input))
                        .collect(Collectors.toList());
            }

            return Bukkit.getOnlinePlayers().stream()
                    .filter(sender::canSee)
                    .map(Player::getName)
                    .filter(name -> name.startsWith(input))
                    .collect(Collectors.toList());
        }
    }

    public static final class PlayerParseException extends ParserException {

        private final String input;

        public PlayerParseException(@NotNull String input, @NotNull CommandContext<?> context) {
            // TODO say player is not online or something idk
            super(PlayerArgument.PlayerParser.class, context, StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_STRING, CaptionVariable.of("input", input));
            this.input = input;
        }

        @NotNull
        public String getInput() {
            return this.input;
        }
    }
}
