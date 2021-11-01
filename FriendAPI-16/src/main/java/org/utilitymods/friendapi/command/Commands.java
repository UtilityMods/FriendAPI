package org.utilitymods.friendapi.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.utilitymods.friendapi.Affinity;
import org.utilitymods.friendapi.BaseProfile;
import org.utilitymods.friendapi.FriendManager;
import org.utilitymods.friendapi.Profile;

import java.util.Locale;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

class Commands {
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
                        BaseProfile player = context.getArgument("name", BaseProfile.class);
                        FriendManager.getInstance().removeFriend(player.uuid);
                        context.getSource().sendFeedback(new LiteralText(player.name + " has been removed").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, COMMAND_BASE + "add " + player.name)).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("add friend")))));
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
            source.sendFeedback(new LiteralText(player.getGameProfile().getName() + " is already a " + affinity.name().toLowerCase(Locale.ROOT)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, player.getGameProfile().getName()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("remove friend")))));
        } else {
            FriendManager.getInstance().addFriend(new Profile(player, affinity));
            source.sendFeedback(new LiteralText(player.getGameProfile().getName() + " is now a " + affinity.name().toLowerCase(Locale.ROOT)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, player.getGameProfile().getName()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("remove friend")))));
        }
        return SINGLE_SUCCESS;
    }

    private int executeList(FabricClientCommandSource source, boolean friends, boolean enemies) {
        for (BaseProfile profile : FriendManager.getInstance().getOnlyAllProfiles()) {
            if (!(profile.affinity.equals(Affinity.FRIEND) && friends) && !(profile.affinity.equals(Affinity.ENEMY) && enemies))
                continue;
            source.sendFeedback(getTextOfProfile(profile));
        }
        return SINGLE_SUCCESS;
    }

    private Text getTextOfProfile(BaseProfile profile) {
        return new LiteralText(getGrayedLabel("Name") + profile.name + " " + getGrayedLabel("Affinity") + getColoredAffinity(profile.affinity)).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("%sremove %s", COMMAND_BASE, profile.name))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("remove"))));
    }

    private String getGrayedLabel(String name) {
        return Formatting.GRAY + name + ": " + Formatting.RESET;
    }

    private String getColoredAffinity(Affinity affinity) {
        Formatting formatting;
        switch (affinity) {
            case NEUTRAL:
                formatting = Formatting.YELLOW;
                break;
            case ENEMY:
                formatting = Formatting.RED;
                break;
            case FRIEND:
                formatting = Formatting.GREEN;
                break;
            default:
                formatting = Formatting.BOLD;
                break;
        }
        return formatting + affinity.name().toLowerCase(Locale.ROOT) + Formatting.RESET;
    }


}
