package org.utilitymods.friendapi.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import org.utilitymods.friendapi.BaseProfile;
import org.utilitymods.friendapi.FriendManager;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FriendArgument implements ArgumentType<BaseProfile> {

    public static FriendArgument friendArgument() {
        return new FriendArgument();
    }

    private final DynamicCommandExceptionType invalidArg = new DynamicCommandExceptionType(o -> new LiteralText(o + " is not a friend"));

    @Override
    public BaseProfile parse(StringReader reader) throws CommandSyntaxException {
        String argument = reader.readString();
        BaseProfile profile = null;
        for (BaseProfile s : FriendManager.getInstance().getFriendMapCopy().values()) {
            if (s.name.equalsIgnoreCase(argument)) {
                profile = s;
                break;
            }
        }
        if (profile == null) throw invalidArg.create(argument);
        return profile;
    }

    @Override
    public Collection<String> getExamples() {
        return FriendManager.getInstance().getFriendMapCopy().values().stream().map(profile -> profile.name).toList();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(getExamples(), builder);
    }

}