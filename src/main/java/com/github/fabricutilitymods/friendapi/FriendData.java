package com.github.fabricutilitymods.friendapi;

import java.util.UUID;

public final class FriendData {

    public String name;
    public FriendType friendType;

    public FriendData(String name, FriendType friendType) {
        this.name = name;
        this.friendType = friendType;
    }
}
