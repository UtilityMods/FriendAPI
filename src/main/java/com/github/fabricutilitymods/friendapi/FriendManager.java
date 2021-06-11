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
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    private final ConcurrentHashMap<UUID, Profile> FRIENDS = new ConcurrentHashMap<>();

    /**
     * Gson Instance
     */
    private final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

    /**
     * Path to Json file
     */
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "friendapi/");
    private final File FILE = new File(FOLDER, "friends.json");

    /**
     * Weather FriendAPI has been loaded or not
     */
    private boolean hasLoaded = false;


    /**
     * Initializes the friend manager.
     */
    public void init() {
        if (hasLoaded) return;

        long start = System.currentTimeMillis();

        LOGGER.info("Using FriendAPI " + VERSION);

        load();
        Runtime.getRuntime().addShutdownHook(new Thread(this::save));
        hasLoaded = true;

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
                JsonArray jsonArray = new JsonParser().parse(reader).getAsJsonArray();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        JsonObject bluh = jsonObject.get(entry.getKey()).getAsJsonObject();
                        String name = bluh.get("Name").getAsString();
                        Affinity affinity = Affinity.valueOf(bluh.get("Affinity").getAsString());
                        addFriend(new Profile(name, UUID.fromString(entry.getKey()), affinity));
                    }
                }
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
        JsonArray jsonElement = new JsonArray();

        for (Map.Entry<UUID, Profile> u : FRIENDS.entrySet()) {
            JsonObject mainObject = new JsonObject();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("Name", new JsonPrimitive(u.getValue().name));
            jsonObject.add("Uuid", new JsonPrimitive(u.getValue().uuid.toString()));
            jsonObject.add("Affinity", new JsonPrimitive(u.getValue().affinity.name()));
            mainObject.add(String.valueOf(u.getKey()), jsonObject);
            jsonElement.add(mainObject);
        }
        output.write(GSON.toJson(jsonElement));
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
