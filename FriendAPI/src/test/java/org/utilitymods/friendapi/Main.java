package org.utilitymods.friendapi;


import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.INSTANCE;
        System.out.println(friendManager.getFriendMapCopy());
        System.out.println("Size: " +  friendManager.getFriendMapCopy().size());
        addFriend(friendManager);
        addFriendWithData(friendManager);
        BaseProfile isk = addEnemyWithMultiData(friendManager);

        System.out.println(friendManager.getFriend(isk.uuid).getData("info"));

        List<String> friendslist = friendManager.getOnlyFriendsProifles().stream().map(baseProfile -> baseProfile.name).collect(Collectors.toList());
        System.out.println(friendslist);
        try {
            System.out.println(BaseProfile.fromUsername("Ethius", Affinity.FRIEND));
            System.out.println(BaseProfile.fromUuid(UUID.fromString("fb482eb2-9751-4479-b07c-df994cffef20"), Affinity.FRIEND));
        } catch (ApiFailedException e) {
            e.printStackTrace();
        }
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
