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
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("FriendAPI");

    /**
     * A map of players' UUIDS to their friend class.
     */
    private final HashMap<UUID, Profile> FRIENDS = new HashMap<>();
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
                InputStream inputStream = Files.newInputStream(FILE.toPath());

                JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
                JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();

                friendObject.forEach(friend -> {
                    String[] values = friend.getAsString().split(":");

                    FRIENDS.put(UUID.fromString(values[0]), new Profile(values[1], UUID.fromString(values[2]), Long.getLong(values[3])));
                });
                inputStream.close();
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
            JsonObject jsonObject = new JsonObject();
            JsonArray friendArray = new JsonArray();
            for (Map.Entry<UUID, Profile> entry : FRIENDS.entrySet()) {
                friendArray.add(entry.getKey().toString() + ":" + entry.getValue().name + ":" + entry.getValue().uuid + ":" + entry.getValue().affinity);
            }
            jsonObject.add("Friends", friendArray);
            output.write(GSON.toJson(new JsonParser().parse(jsonObject.toString())));
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
