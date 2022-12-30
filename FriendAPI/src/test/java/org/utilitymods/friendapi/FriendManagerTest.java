package org.utilitymods.friendapi;

import org.junit.jupiter.api.Test;
import org.utilitymods.friendapi.exceptions.ApiFailedException;
import org.utilitymods.friendapi.profiles.Profile;

import java.util.UUID;

class FriendManagerTest {

    @Test
    void testFriendList() {
        FriendManager friendManager = FriendManager.getInstance();
        System.out.println(friendManager.getFriendMapCopy());
        System.out.println("Size: " +  friendManager.getFriendMapCopy().size());
    }

    @Test
    void testFactory() {
        FriendManager friendManager = FriendManager.getInstance();
        UUID uuid = UUID.randomUUID();
        Profile friend = friendManager.getFactory().createFriend("test", uuid);
        assert friend != null;
        assert friend.getName().equals("test");
        assert friend.getUUID().equals(uuid);
        friendManager.addFriend(friend);
        assert friendManager.getFriendMapCopy().size() != 0;
        assert friendManager.isFriend(friend.getUUID());
        friendManager.removeFriend(friend.getUUID());
    }


    @Test
    void testUUIDProfile() {
        FriendManager friendManager = FriendManager.getInstance();
        Profile friend = null;
        try {
            friend = friendManager.getFactory().createProfile(UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306"));
        } catch (ApiFailedException e) {
            e.printStackTrace();
        }
        assert friend != null;
        assert friend.getName().equals("ChiquitaV2");
        assert friend.getUUID().equals(UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306"));
    }

    @Test
    void testNameProfile() {
        FriendManager friendManager = FriendManager.getInstance();
        Profile friend = null;
        try {
            friend = friendManager.getFactory().createProfile("ChiquitaV2");
        } catch (ApiFailedException e) {
            e.printStackTrace();
        }
        assert friend != null;
        assert friend.getName().equals("ChiquitaV2");
        assert friend.getUUID().equals(UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306"));
    }
}
