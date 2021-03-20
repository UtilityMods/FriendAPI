package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;

public final class Friend implements FriendData {

    public GameProfile gameProfile;
    public FriendType friendType;
    /* 
    * What priority this Friend has, by default this is 0, indicating no priority. Priority could be used as a way to sort Friends when
    * needed and allow the user to choose who they want to have a higher and lower priority. If two friends have the same priority then it will be random.
    * This isn't a necessity but could be a neat feature if you want to hook it into your client.
    */
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
    
    @Override
    public void setType(FriendType friendType) {
        this.friendType = friendType;
    }
    
    @Override
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
