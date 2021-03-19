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

    public final Logger LOGGER = LogManager.getLogger("FriendAPI");

    private final HashMap<UUID, FriendData> FRIENDS = new HashMap<>();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "FriendAPI/");
    private final File FILE = new File(FOLDER, "Friends.json");

    public void load() {
        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
        }
        try {
            if (FILE.exists()) {
                Reader reader = Files.newBufferedReader(FILE.toPath());
                FRIENDS.putAll(
                        new Gson().fromJson(reader,
                        new TypeToken<HashMap<UUID, FriendData>>() {}.getType())
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

    public HashMap<UUID, FriendData> getFriendMapCopy() {
        return new HashMap<>(FRIENDS);
    }

    public FriendData addFriend(UUID uuid, String name, FriendType friendType) {
        FriendData data = new FriendData(name, friendType);
        FRIENDS.put(uuid, data);
        return data;
    }

    public FriendData getFriendData(UUID uuid){
        return FRIENDS.get(uuid);
    }

    public boolean removeFriend(UUID uuid) {
        boolean exists = FRIENDS.containsKey(uuid);
        FRIENDS.remove(uuid);
        return exists;
    }

    public boolean isFriend(UUID uuid) {
        FriendType type = FRIENDS.get(uuid).friendType;
        return type == FriendType.FRIEND
                || type == FriendType.BEST_FRIEND;
    }
}
