package com.github.fabricutilitymods.friendapi;

import java.util.UUID;

public final class Friend implements FriendData {

    public String name;
    public UUID uuid;
    public FriendType friendType;

    public Friend(UUID uuid, String name, FriendType friendType) {
        this.uuid = uuid;
        this.name = name;
        this.friendType = friendType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public FriendType getType() {
        return friendType;
    }
}
