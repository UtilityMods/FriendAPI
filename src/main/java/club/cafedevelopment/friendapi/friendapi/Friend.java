package club.cafedevelopment.friendapi.friendapi;

import java.util.UUID;

/**
 * @author yagel15637
 */
public final class Friend {
    private final UUID uuid;
    private FriendType friendType;

    public Friend(UUID uuid, FriendType friendType) {
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
