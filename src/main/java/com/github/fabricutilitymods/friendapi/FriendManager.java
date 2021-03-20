package com.github.fabricutilitymods.friendapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

/**
 * Main class and manager of the Friend API.
 */
public final class FriendManager {

    /**
     * The constant INSTANCE of the friend manager.
     */
    public static final FriendManager INSTANCE = new FriendManager();

    /**
     * The Logger.
     */
    public final Logger LOGGER = LogManager.getLogger("FriendAPI");

    /**
     * A map of players' UUIDS to their friend class.
     */
    private final HashMap<UUID, Friend> FRIENDS = new HashMap<>();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "friendapi/");
    private final File FILE = new File(FOLDER, "friends.json");

    /**
     * Initializes the friend manager.
     */
    public void init() {
        long start = System.currentTimeMillis();

        LOGGER.info("Using FriendAPI v1.0");

        load();

        Runtime.getRuntime().addShutdownHook(new Thread(this::save));

        LOGGER.info("FriendAPI started in " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * Loads friends.json into FRIENDS hashmap.
     */
    public void load() {
        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
        }
        try {
            if (FILE.exists()) {
                Reader reader = Files.newBufferedReader(FILE.toPath());
                FRIENDS.putAll(
                        new Gson().fromJson(reader,
                                new TypeToken<HashMap<UUID, Friend>>() {
                                }.getType())
                );
                reader.close();
            }
        } catch (Exception e) {
            LOGGER.fatal("Failed to load \"" + FILE.getAbsolutePath() + "\"!");
        }
    }

    /**
     * Saves the FRIENDS hashmap into friends.json.
     */
    public void save() {
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8);
            output.write(GSON.toJson(FRIENDS));
            output.close();
        } catch (IOException e) {
            LOGGER.fatal("Failed to save \"" + FILE.getAbsolutePath() + "\"!");
        }
    }

    /**
     * Gets friend map copy.
     *
     * @return a copy of the FRIENDS hashmap
     */
    @NotNull
    public HashMap<UUID, Friend> getFriendMapCopy() {
        return new HashMap<>(FRIENDS);
    }

    /**
     * Gets a friend from the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player to search for
     * @return the friend
     */
    @NotNull
    public Friend getFriend(UUID uuid) {
        return FRIENDS.getOrDefault(uuid, Friend.getEmpty());
    }

    /**
     * Gets a uuid from a Friend object.
     *
     * @param friend a Friend object
     * @return the uuid of the Friend object's owner
     */
    @NotNull
    public UUID getUUID(Friend friend) {
        return friend.gameProfile.getId();
    }

    /**
     * Add or replace a friend in the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player to register
     * @param data the data of the player to register
     * @return the newly created friend
     */
    @NotNull
    public Friend addFriend(UUID uuid, Friend data) {
        FRIENDS.put(uuid, data);
        return getFriend(uuid);
    }

    /**
     * Removes friend from the FRIENDS hashmap.
     *
     * @param uuid the uuid of the friend to remove
     */
    public void removeFriend(UUID uuid) {
        FRIENDS.remove(uuid);
    }

    /**
     * Gets friend type from the FRIENDS hashmap.
     *
     * @param uuid the uuid
     * @return the registered friend type
     */
    @NotNull
    public Long getAffinity(UUID uuid) {
        return getFriend(uuid).affinity;
    }

    /**
     * Checks if player with specified UUID is a friend according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered friend
     */
    public boolean isFriend(UUID uuid) {
        return getAffinity(uuid) > 0L;
    }

    /**
     * Checks if player with specified UUID is an enemy according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered enemy
     */
    public boolean isEnemy(UUID uuid) {
        return getAffinity(uuid) < 0L;
    }

    /**
     * Checks if player with specified UUID is neutral or unregistered in the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is neutral or unregistered
     */
    public boolean isNeutral(UUID uuid) {
        return getAffinity(uuid) == 0L;
    }

}
