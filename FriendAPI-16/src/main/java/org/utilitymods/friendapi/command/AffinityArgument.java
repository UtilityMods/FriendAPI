package org.utilitymods.friendapi.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import org.utilitymods.friendapi.profiles.Affinity;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class AffinityArgument implements ArgumentType<Affinity> {
    private static final Collection<String> EXAMPLES = Arrays.asList("friend", "enemy", "neutral");

    private AffinityArgument() {
    }

    public static AffinityArgument affinity() {
        return new AffinityArgument();
    }

    public static Affinity getAffinity(CommandContext<?> context, String name) {
        return context.getArgument(name, Affinity.class);
    }

    public Affinity parse(StringReader stringReader) {
        String string = stringReader.readUnquotedString();
        Affinity affinity = Affinity.byName(string);
        if (affinity != null) {
            return affinity;
        } else {
            return Affinity.NEUTRAL;
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(getExamples(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
