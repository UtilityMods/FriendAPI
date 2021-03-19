package com.github.fabricutilitymods.friendapi;

import java.util.UUID;

public final class FriendData {

    private final UUID uuid;
    private FriendType friendType;

    public FriendData(UUID uuid, FriendType friendType) {
        this.uuid = uuid;
        this.friendType = friendType;
    }

    public UUID getUUID() {
        return uuid;
    }

    public FriendType getFriendType() {
        return friendType;
    }

    public void setFriendType(FriendType friendType) {
        this.friendType = friendType;
    }
}
