package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;

public interface FriendData {

    GameProfile getProfile();

    FriendType getType();

}
