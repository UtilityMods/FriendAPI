package org.utilitymods.friendapi.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PlayerArgument implements ArgumentType<PlayerEntity> {
    private static final Collection<String> EXAMPLES = (Arrays.asList("Player", "ChiquitaV2", "Joe", "Dream"));
    private final MinecraftClient mc;

    public PlayerArgument(MinecraftClient mc) {
        this.mc = mc;
    }

    @Override
    public PlayerEntity parse(StringReader reader) throws CommandSyntaxException {
        String argument = reader.readString();
        PlayerEntity playerEntity = null;
        assert mc.world != null;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p.getDisplayName().getContent().equalsIgnoreCase(argument)) {
                playerEntity = p;
                break;
            }
        }
        if (playerEntity == null) throw EntityArgumentType.NOT_ALLOWED_EXCEPTION.createWithContext(reader);
        return playerEntity;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSource commandSource) {
            StringReader stringReader = new StringReader(builder.getInput());
            stringReader.setCursor(builder.getStart());

            return CommandSource.suggestMatching(commandSource.getPlayerNames(), builder);
        } else {
            return Suggestions.empty();
        }

    }

    public static PlayerEntity getPlayer(CommandContext<?> context, String name) {
        return context.getArgument(name, PlayerEntity.class);
    }
}
