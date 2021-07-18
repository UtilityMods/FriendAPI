package org.utilitymods.friendapi.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.utilitymods.friendapi.BaseProfile;
import org.utilitymods.friendapi.FriendManager;
import org.utilitymods.friendapi.Profile;

import java.util.UUID;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FriendMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ArgumentTypes.register("friendapi:friend", FriendArgument.class, new ConstantArgumentSerializer<>(FriendArgument::friendArgument));

        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("friendapi")
                .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("name", new GameProfileArgumentType())
                                .executes(context -> {
                                    PlayerEntity player = context.getArgument("name", PlayerEntity.class);
                                    if (FriendManager.INSTANCE.isFriend(player.getUuid())) {
                                        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(player.getGameProfile().getName() + " is already a friend"));
                                    } else {
                                        FriendManager.INSTANCE.addFriend(new Profile(player));
                                        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(player.getGameProfile().getName() + " is now a friend"));
                                    }
                                    return SINGLE_SUCCESS;
                                })))
                .then(ClientCommandManager.literal("remove")
                        .then(ClientCommandManager.argument("name", new FriendArgument())
                        .executes(context -> {
                                    BaseProfile player = context.getArgument("name", BaseProfile.class);
                                    FriendManager.INSTANCE.removeFriend(player.uuid);
                                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(player + " is no longer a friend"));
                                    return SINGLE_SUCCESS;
                                })))
                .then(ClientCommandManager.literal("list")
                        .executes(context -> {
                            for (BaseProfile profile : FriendManager.INSTANCE.getFriendMapCopy().values()) {
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(profile.name));
                            }
                            return SINGLE_SUCCESS;
                        })));
    }
}
