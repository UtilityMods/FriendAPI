package org.utilitymods.friendapi.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralTextContent;
import org.utilitymods.friendapi.profiles.Profile;
import org.utilitymods.friendapi.FriendManager;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FriendArgument implements ArgumentType<Profile> {

    public static FriendArgument friendArgument() {
        return new FriendArgument();
    }

    private final DynamicCommandExceptionType invalidArg = new DynamicCommandExceptionType(o -> new LiteralTextContent(o + " is not a friend"));

    @Override
    public Profile parse(StringReader reader) throws CommandSyntaxException {
        String argument = reader.readString();
        Profile profile = null;
        for (Profile s : FriendManager.getInstance().getFriendMapCopy().values()) {
            if (s.getName().equalsIgnoreCase(argument)) {
                profile = s;
                break;
            }
        }
        if (profile == null) throw invalidArg.create(argument);
        return profile;
    }

    @Override
    public Collection<String> getExamples() {
        return FriendManager.getInstance().getFriendMapCopy().values().stream().map(Profile::getName).toList();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(getExamples(), builder);
    }

}