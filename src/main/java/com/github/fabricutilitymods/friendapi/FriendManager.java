package com.github.fabricutilitymods.friendapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

public final class FriendManager {

    public static final FriendManager INSTANCE = new FriendManager();

    public final Logger LOGGER = LogManager.getLogger("FriendAPI");

    private final HashMap<UUID, Friend> FRIENDS = new HashMap<>();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "friendapi/");
    private final File FILE = new File(FOLDER, "friends.json");

    public void init(){
        long start = System.currentTimeMillis();

        LOGGER.info("Using FriendAPI v1.0");

        load();

        Runtime.getRuntime().addShutdownHook(new Thread(this::save));

        LOGGER.info("FriendAPI started in " + (System.currentTimeMillis() - start) + "ms");
    }

    public void load() {
        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
        }
        try {
            if (FILE.exists()) {
                Reader reader = Files.newBufferedReader(FILE.toPath());
                FRIENDS.putAll(
                        new Gson().fromJson(reader,
                        new TypeToken<HashMap<UUID, Friend>>() {}.getType())
                );
                reader.close();
            }
        } catch (Exception e){
            LOGGER.fatal("Failed to load \""+FILE.getAbsolutePath()+"\"!");
        }
    }

    public void save() {
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8);
            output.write(GSON.toJson(FRIENDS));
            output.close();
        } catch (IOException e){
            LOGGER.fatal("Failed to save \""+FILE.getAbsolutePath()+"\"!");
        }
    }

    public HashMap<UUID, Friend> getFriendMapCopy() {
        return new HashMap<>(FRIENDS);
    }

    public Friend addFriend(UUID uuid, Friend data) {
        FRIENDS.put(uuid, data);
        return FRIENDS.get(uuid);
    }

    public Friend getFriend(UUID uuid){
        return FRIENDS.get(uuid);
    }

    public boolean removeFriend(UUID uuid) {
        FRIENDS.remove(uuid);
        return FRIENDS.containsKey(uuid);
    }

    public boolean isFriend(UUID uuid) {
        FriendType type = FRIENDS.get(uuid).friendType;
        return type == FriendType.FRIEND || type == FriendType.BEST_FRIEND;
    }

}
