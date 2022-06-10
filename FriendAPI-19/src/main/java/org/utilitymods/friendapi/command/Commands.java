package org.utilitymods.friendapi.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.utilitymods.friendapi.FabricProfileFactory;
import org.utilitymods.friendapi.profiles.Affinity;
import org.utilitymods.friendapi.profiles.Profile;
import org.utilitymods.friendapi.FriendManager;

import java.util.Locale;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class Commands {
    private static final String COMMAND_BASE = "/friendapi ";

    public final LiteralCommandNode<FabricClientCommandSource> add = ClientCommandManager.literal("add")
            .then(ClientCommandManager.argument("name", new PlayerArgument(MinecraftClient.getInstance()))
                    .executes(context -> executeAdd(context.getSource(), PlayerArgument.getPlayer(context, "name"), Affinity.FRIEND))
                    .then(ClientCommandManager.argument("affinity", AffinityArgument.affinity())
                            .executes(context -> executeAdd(context.getSource(), PlayerArgument.getPlayer(context, "name"), AffinityArgument.getAffinity(context, "affinity"))))
            ).build();

    public final LiteralCommandNode<FabricClientCommandSource> remove = ClientCommandManager.literal("remove")
            .then(ClientCommandManager.argument("name", new FriendArgument())
                    .executes(context -> {
                        Profile player = context.getArgument("name", Profile.class);
                        FriendManager.getInstance().removeFriend(player.getUUID());
                        context.getSource().sendFeedback(new TranslatableTextContent("commands.friendapi.removed", player.getName()).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, COMMAND_BASE + "add " + player.getName())).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableTextContent("commands.friendapi.add")))));
                        return SINGLE_SUCCESS;
                    })).build();

    public final LiteralCommandNode<FabricClientCommandSource> list = ClientCommandManager.literal("list")
            .then(ClientCommandManager.literal("friends")
                    .executes(context -> executeList(context.getSource(), true, false)))
            .then(ClientCommandManager.literal("enemies")
                    .executes(context -> executeList(context.getSource(), false, true)))
            .executes(context -> executeList(context.getSource(), true, true))
            .build();

    private int executeAdd(FabricClientCommandSource source, PlayerEntity player, Affinity affinity) {
        if (FriendManager.getInstance().getAffinity(player.getGameProfile().getId()).equals(affinity)) {

            source.sendFeedback(new TranslatableTextContent("commands.friendapi.already", player.getGameProfile().getName(), affinity.name().toLowerCase(Locale.ROOT)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, player.getGameProfile().getName()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableTextContent("commands.friendapi.remove")))));
        } else {
            FriendManager.getInstance().addFriend(FabricProfileFactory.INSTANCE.createProfile(player, affinity));

            source.sendFeedback(new TranslatableTextContent("commands.friendapi.now", player.getGameProfile().getName(), affinity.name().toLowerCase(Locale.ROOT)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, player.getGameProfile().getName()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableTextContent("commands.friendapi.remove")))));
        }
        return SINGLE_SUCCESS;
    }

    private int executeList(FabricClientCommandSource source, boolean friends, boolean enemies) {
        for (Profile profile : FriendManager.getInstance().getOnlyAllProfiles()) {
            if (!(profile.getAffinity().equals(Affinity.FRIEND) && friends) && !(profile.getAffinity().equals(Affinity.ENEMY) && enemies))
                continue;
            source.sendFeedback(getTextOfProfile(profile));
        }
        return SINGLE_SUCCESS;
    }

    private Text getTextOfProfile(Profile profile) {
        return new LiteralTextContent(getGrayedLabel("Name") + profile.getName() + " " + getGrayedLabel("Affinity") + getColoredAffinity(profile.getAffinity())).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, profile.getName()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableTextContent("commands.friendapi.remove"))));
    }

    private String getGrayedLabel(String name) {
        return Formatting.GRAY + name + ": " + Formatting.RESET;
    }

    private String getColoredAffinity(Affinity affinity) {
        Formatting formatting = switch (affinity) {
            case NEUTRAL -> Formatting.YELLOW;
            case ENEMY -> Formatting.RED;
            case FRIEND -> Formatting.GREEN;
        };
        return formatting + affinity.name().toLowerCase(Locale.ROOT) + Formatting.RESET;
    }


}
