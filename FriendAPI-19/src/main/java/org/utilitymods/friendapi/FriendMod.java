package org.utilitymods.friendapi;

import net.fabricmc.api.ModInitializer;
//import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import org.utilitymods.friendapi.FriendManager;
import org.utilitymods.friendapi.command.Commands;
//import org.utilitymods.friendapi.command.FriendArgument;

public class FriendMod implements ModInitializer {

    @Override
    public void onInitialize() {
        FriendManager.getInstance();

        Commands commands = new Commands();

//        ArgumentTypes.register("friendapi:friend", FriendArgument.class, new ConstantArgumentSerializer<>(FriendArgument::friendArgument));
//
//        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("friendapi")
//                .then(commands.add)
//                .then(commands.remove)
//                .then(commands.list));
    }

}
