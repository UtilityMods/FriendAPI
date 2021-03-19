package com.github.fabricutilitymods.friendapi;

import java.util.UUID;

public final class FriendData {

    public UUID uuid;
    public FriendType friendType;

    public FriendData(UUID uuid, FriendType friendType) {
        this.uuid = uuid;
        this.friendType = friendType;
    }
}
