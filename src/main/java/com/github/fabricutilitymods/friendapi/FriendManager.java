package com.github.fabricutilitymods.friendapi;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class and manager of the Friend API.
 */
public final class FriendManager {

    /**
     * Mod Version
     */
    private static final String VERSION = "v1.2";

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("FriendAPI");

    /**
     * The constant INSTANCE of the friend manager.
     */
    public static final FriendManager INSTANCE = new FriendManager();

    /**
     * A map of players' UUIDS to their friend class.
     */
    private ConcurrentHashMap<UUID, Profile> FRIENDS = new ConcurrentHashMap<>();

    /**
     * Gson Instance
     */
    public final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

    /**
     * TypeToken
     */
    private final Type type = new TypeToken<ConcurrentHashMap<String, Profile>>(){}.getType();

    /**
     * Path to folder
     */
    private final Path FOLDER = FabricLoader.getInstance().getGameDir().resolve("friendapi");

    /**
     * Path to Json file
     */
    private final File FILE = new File(FOLDER.toFile(), "friends.json");

    /**
     * Initializes the friend manager.
     */
    public FriendManager() {

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
        try {
            if (!FILE.exists()) {
                save();
            }
            if (FILE.exists()) {
                Reader reader = Files.newBufferedReader(FILE.toPath());
                FRIENDS = GSON.fromJson(reader, type);
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
        if (!FOLDER.toFile().exists()) {
            try {
                Files.createDirectories(FOLDER);
            } catch (IOException e) {
                LOGGER.fatal("Failed to make \"" + FOLDER + "\"!");
            }
        }
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8);
            output.write(GSON.toJson(FRIENDS));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets friend map copy.
     *
     * @return a copy of the FRIENDS hashmap
     */
    @NotNull
    public Map<UUID, Profile> getFriendMapCopy() {
        return Collections.unmodifiableMap(FRIENDS) ;
    }

    /**
     * Gets a friend from the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player to search for
     * @return the friend
     */
    @NotNull
    public Profile getFriend(@NotNull UUID uuid) {
        return FRIENDS.getOrDefault(uuid, new Profile("empty", uuid, Affinity.NEUTRAL));
    }

    /**
     * Add or replace a friend in the FRIENDS hashmap.
     *
     * @param profile the profile of the player to register
     * @return the newly created friend
     */
    public Profile addFriend(Profile profile) {
          return FRIENDS.compute(profile.uuid, ((uuid, profile1) -> profile1 = profile));
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
    public Affinity getAffinity(@NotNull UUID uuid) {
        return getFriend(uuid).affinity;
    }

    /**
     * Checks if player with specified UUID is a friend according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered friend
     */
    public boolean isFriend(@NotNull UUID uuid) {
        return getAffinity(uuid).type > 0;
    }

    /**
     * Checks if player with specified UUID is an enemy according to the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is a registered enemy
     */
    public boolean isEnemy(@NotNull UUID uuid) {
        return getAffinity(uuid).type < 0;
    }

    /**
     * Checks if player with specified UUID is neutral or unregistered in the FRIENDS hashmap.
     *
     * @param uuid the uuid of the player
     * @return whether the player queried is neutral or unregistered
     */
    public boolean isNeutral(@NotNull UUID uuid) {
        return getAffinity(uuid).type == 0;
    }

}
