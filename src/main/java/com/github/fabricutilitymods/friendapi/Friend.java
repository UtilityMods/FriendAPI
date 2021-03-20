package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;

public final class Friend implements FriendData {

    public GameProfile gameProfile;
    public FriendType friendType;
    public int priority;

    public Friend(GameProfile gameProfile, FriendType friendType) {
        this.gameProfile = gameProfile;
        this.friendType = friendType;
        this.priority = 0;
    }
    
    public Friend(GameProfile gameProfile, FriendType friendType, int priority) {
        this.gameProfile = gameProfile;
        this.friendType = friendType;
        this.priority = priority;
    }

    @Override
    public GameProfile getProfile(){
        return gameProfile;
    }

    @Override
    public FriendType getType() {
        return friendType;
    }
}
