package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;

public final class Friend implements FriendData {

    public GameProfile gameProfile;
    public FriendType friendType;

    public Friend(GameProfile gameProfile, FriendType friendType) {
        this.gameProfile = gameProfile;
        this.friendType = friendType;
    }

    @Override
    public GameProfile getProfile(){
        return gameProfile;
    }

    @Override
    public FriendType getType() {
        return friendType;
    }
    
    @Override
    public void setType(FriendType friendType) {
        this.friendType = friendType;
    }
}
