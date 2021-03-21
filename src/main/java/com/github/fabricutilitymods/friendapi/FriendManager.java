package com.github.fabricutilitymods.friendapi;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
     * Mod Version
     */
    private static final String VERSION = "v1.2";

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("FriendAPI");

    /**
     * A map of players' UUIDS to their friend class.
     */
    private final HashMap<UUID, Profile> FRIENDS = new HashMap<>();

    /**
     * Gson Instance
     */
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Path to Json save location
     */
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "friendapi/");

    /**
     * Path to Json file
     */
    private final File FILE = new File(FOLDER, "friends.json");

    /**
     * Initializes the friend manager.
     */
    public void init() {
        long start = System.currentTimeMillis();

        LOGGER.info("Using FriendAPI " + VERSION);

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
                Type type = new TypeToken<HashMap<UUID, Profile>>() {}.getType();
                FRIENDS.putAll(GSON.fromJson(reader, type));
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
    public HashMap<UUID, Profile> getFriendMapCopy() {
        return new HashMap<>(FRIENDS);
    }

    /**
     * Gets a friend from the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player to search for
     * @return the friend
     */
    @NotNull
    public Profile getFriend(@NotNull UUID uuid) {
        return FRIENDS.getOrDefault(uuid, new Profile("empty", uuid, 0L));
    }

    /**
     * Add or replace a friend in the FRIENDS hashmap.
     *
     * @param profile the profile of the player to register
     * @return the newly created friend
     */
    @NotNull
    public Profile addFriend(Profile profile) {
        FRIENDS.put(profile.uuid, profile);
        return getFriend(profile.uuid);
    }

    /**
     * Removes friend from the FRIENDS hashmap.
     *
     * @param uuid the uuid of the friend to remove
     */
    public void removeFriend(@NotNull UUID uuid) {
        FRIENDS.remove(uuid);
    }

    /**
     * Gets friend type from the FRIENDS hashmap.
     *
     * @param uuid the uuid
     * @return the registered friend type
     */
    @NotNull
    public Long getAffinity(@NotNull UUID uuid) {
        return getFriend(uuid).affinity;
    }

    /**
     * Checks if player with specified UUID is a friend according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered friend
     */
    public boolean isFriend(@NotNull UUID uuid) {
        return getAffinity(uuid) > 0L;
    }

    /**
     * Checks if player with specified UUID is an enemy according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered enemy
     */
    public boolean isEnemy(@NotNull UUID uuid) {
        return getAffinity(uuid) < 0L;
    }

    /**
     * Checks if player with specified UUID is neutral or unregistered in the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is neutral or unregistered
     */
    public boolean isNeutral(@NotNull UUID uuid) {
        return getAffinity(uuid) == 0L;
    }

}
