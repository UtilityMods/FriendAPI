package org.utilitymods.friendapi;


import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.INSTANCE;
        System.out.println("Size: " +  friendManager.getFriendMapCopy().size());
        addFriend(friendManager);
        addFriendWithData(friendManager);
        BaseProfile isk = addEnemyWithMultiData(friendManager);

        System.out.println(friendManager.getFriend(isk.uuid).getData("info"));

    }

    private static BaseProfile addFriend(FriendManager friendManager) {
        BaseProfile profile = new BaseProfile("ChiquitaV2", UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306"));
        return friendManager.addFriend(profile);
    }

    private static BaseProfile addFriendWithData(FriendManager friendManager) {
        BaseProfile profile = new BaseProfile("DoleV2", UUID.fromString("c812b134-9147-48e6-9f04-6a0604deafd7"));
        ProfileData data = new ProfileData("ChiquitaV2", 69);
        profile.addData("info", data);
        return friendManager.addFriend(profile);
    }

    private static BaseProfile addEnemyWithMultiData(FriendManager friendManager) {
        BaseProfile profile = new BaseProfile("ItsukiSumeragi", UUID.fromString("71fb3abc-f1dc-481d-a639-5631e7cd12ea"), Affinity.ENEMY);
        ProfileData data = new ProfileData("DoleV2", 1);
        profile.addData("info", data);
        profile.addData("notes", "Very based");
        return friendManager.addFriend(profile);
    }

}
