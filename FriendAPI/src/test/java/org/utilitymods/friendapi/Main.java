package org.utilitymods.friendapi;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.INSTANCE;

        for (int i = 0; i < 10; i++) {
            BaseProfile profile = new BaseProfile("Boop" + i, UUID.randomUUID());
            friendManager.addFriend(profile);

        }

        //System.out.println(friendManager.getFriendMapCopy().get(UUID.fromString("02a2b5f6-7fb7-4a86-af7c-3b735509ad1e")).affinity);
        //System.out.println(friendManager.isFriend(UUID.fromString("02a2b5f6-7fb7-4a86-af7c-3b735509ad1e")));
        System.out.println(friendManager.getFriendMapCopy());
        System.out.println(UUID.fromString("02a2b5f6-7fb7-4a86-af7c-3b735509ad1e").equals(UUID.fromString("02a2b5f6-7fb7-4a86-af7c-3b735509ad1e")));

    }


}
