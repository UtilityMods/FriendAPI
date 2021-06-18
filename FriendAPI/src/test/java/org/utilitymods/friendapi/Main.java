package org.utilitymods.friendapi;


import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.INSTANCE;
        System.out.println("Size: " +  friendManager.getFriendMapCopy().size());
        for (int i = 0; i < 10; i++) {
            BaseProfile profile = new BaseProfile("Femboy#" + i, UUID.randomUUID());
            friendManager.addFriend(profile);
        }


    }


}
