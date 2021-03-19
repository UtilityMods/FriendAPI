package com.github.fabricutilitymods.friendapi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FriendManager {
    public static final FriendManager INSTANCE = new FriendManager();

    private final List<Friend> friends = new ArrayList<>();

    public void init() {

    }
    // TODO save and load friends.
    public void save() {

    }

    public FriendType getFriendType(UUID uuid) {
        for (Friend friend : friends) {
            if (friend.getUUID().equals(uuid))
                return friend.getFriendType();
        }

        return FriendType.NONE;
    }

    public void setFriendType(UUID uuid, FriendType friendType) {
        for (Friend friend : friends) {
            if (friend.getUUID().equals(uuid)) {
                friend.setFriendType(friendType);

                return;
            }
        }

        friends.add(new Friend(uuid, friendType));
    }

    public void removeFriend(UUID uuid) {
        for (Friend friend : friends) {
            if (friend.getUUID().equals(uuid)) {
                friend.setFriendType(FriendType.NONE);

                return;
            }
        }
    }

    public boolean isFriend(UUID uuid) {
        for (Friend friend : friends) {
            if (friend.getUUID().equals(uuid))
                return friend.getFriendType() == FriendType.FRIEND || friend.getFriendType() == FriendType.BEST_FRIEND;
        }

        return false;
    }

    private FriendManager() {}
}
