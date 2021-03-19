package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public interface FriendData {

    FriendType getType();

    GameProfile getProfile();

}
