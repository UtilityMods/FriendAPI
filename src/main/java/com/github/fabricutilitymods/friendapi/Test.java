package com.github.fabricutilitymods.friendapi;

import net.fabricmc.api.ModInitializer;

import java.util.UUID;

public class Test implements ModInitializer {
    @Override
    public void onInitialize() {
        FriendManager friendManager = FriendManager.INSTANCE;
        friendManager.addFriend(new Profile("test", UUID.randomUUID()));
    }
}
