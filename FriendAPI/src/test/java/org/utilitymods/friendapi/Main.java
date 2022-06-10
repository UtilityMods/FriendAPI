package org.utilitymods.friendapi;

import org.utilitymods.friendapi.exceptions.ApiFailedException;
import org.utilitymods.friendapi.profiles.Affinity;
import org.utilitymods.friendapi.profiles.Profile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    /*
     * Initialize tests
     */
    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.getInstance();
        System.out.println(friendManager.getFriendMapCopy());
        System.out.println("Size: " +  friendManager.getFriendMapCopy().size());
        addFriend(friendManager);
        addFriendWithData(friendManager);
        Profile isk = addEnemyWithMultiData(friendManager);

        System.out.println(friendManager.getFriend(isk.getUUID()).getData("info"));
        List<String> friendslist = friendManager.getOnlyFriendsProfiles().stream().map(Profile::getName).collect(Collectors.toList());
        System.out.println(friendslist);
        try {
            System.out.println(friendManager.getFactory().createFriend("Ethius"));
            System.out.println(friendManager.getFactory().createFriend(UUID.fromString("fb482eb2-9751-4479-b07c-df994cffef20")));
        } catch (ApiFailedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Add a friend
     */
    private static Profile addFriend(FriendManager friendManager) {
        Profile profile = new Profile("ChiquitaV2", UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306"));
        return friendManager.addFriend(profile);
    }

    /*
     * Add a friend with data
     */
    private static Profile addFriendWithData(FriendManager friendManager) {
        Profile profile = new Profile("DoleV2", UUID.fromString("c812b134-9147-48e6-9f04-6a0604deafd7"));
        ProfileData data = new ProfileData("ChiquitaV2", 69);
        profile.editData("info", data);
        return friendManager.addFriend(profile);
    }

    /*
     * Add an enemy with multiple data
     */
    private static Profile addEnemyWithMultiData(FriendManager friendManager) {
        Profile profile = new Profile("ItsukiSumeragi", UUID.fromString("71fb3abc-f1dc-481d-a639-5631e7cd12ea"), Affinity.ENEMY);
        ProfileData data = new ProfileData("DoleV2", 1);
        profile.editData("info", data);
        profile.editData("notes", "Very based");
        return friendManager.addFriend(profile);
    }

}
