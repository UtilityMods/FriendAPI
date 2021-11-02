package org.utilitymods.friendapi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MigrationTest {

    /*
     * Test the migration of the database.
     */
    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.getInstance();
        MigrationTest migrationTest = new MigrationTest();

        migrationTest.migrateUsernameList(friendManager);
        migrationTest.migrateUuidList(friendManager);
    }

    /*
     * Migrate the username list.
     */
    private void migrateUsernameList(FriendManager friendManager) {
        List<String> usernameList = new ArrayList<>();
        usernameList.add("olliem5");
        usernameList.add("Reap1");
        usernameList.add("linustouchtips24");

        friendManager.migrateFromNameList(usernameList);
    }

    /*
     * Migrate the uuid list.
     */
    private void migrateUuidList(FriendManager friendManager) {
        List<UUID> uuidList = new ArrayList<>();
        uuidList.add(UUID.fromString("8f2a4be6-2717-42b9-a7ed-070489b49b56"));
        uuidList.add(UUID.fromString("5acf9e95-cde1-434d-a3e3-7573cec1dc53"));
        uuidList.add(UUID.fromString("10f3f5bd-88e0-442c-8d72-6ee44498d1fc"));

        friendManager.migrateFromUuidList(uuidList);
    }

}
