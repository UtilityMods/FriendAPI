package com.github.fabricutilitymods.friendapi;

import net.fabricmc.api.ModInitializer;

import java.util.UUID;

public class Test implements ModInitializer {

    @Override
    public void onInitialize() {
        FriendManager.INSTANCE.save();

        System.out.println(FriendManager.INSTANCE.isFriend(UUID.fromString("13bb84f8-f700-42f4-ad65-60e759ff0e3f")));
    }
}
