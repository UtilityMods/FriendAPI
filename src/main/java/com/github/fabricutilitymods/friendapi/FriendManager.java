package com.github.fabricutilitymods.friendapi;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class FriendManager {

    private final List<Friend> FRIENDS = new ArrayList<>();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString().replaceAll("\\.", "") + "FriendAPI/");
    private final File FILE = new File(FOLDER, "Friends.json");

    public void load() {
        if(!FOLDER.exists()) FOLDER.mkdirs();
        try {
            if(FILE.exists()){
                Reader reader = Files.newBufferedReader(FILE.toPath());
                FRIENDS.addAll(
                        new Gson().fromJson(reader,
                        new TypeToken<List<Friend>>() {}.getType())
                );
                reader.close();
            }
        } catch (Exception e){
            FriendAPI.LOGGER.fatal("Failed to load \""+FILE.getAbsolutePath()+"\"!");
        }
    }

    public void save() {
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8);
            output.write(GSON.toJson(FRIENDS));
            output.close();
        } catch (IOException e){
            FriendAPI.LOGGER.fatal("Failed to save \""+FILE.getAbsolutePath()+"\"!");
        }
    }

    public FriendType getFriendType(UUID uuid) {
        for (Friend friend : FRIENDS) {
            if (friend.getUUID().equals(uuid))
                return friend.getFriendType();
        }
        return FriendType.NONE;
    }

    public void setFriendType(UUID uuid, FriendType friendType) {
        for (Friend friend : FRIENDS) {
            if (friend.getUUID().equals(uuid)) {
                friend.setFriendType(friendType);
                break;
            }
        }
        FRIENDS.add(new Friend(uuid, friendType));
    }

    public void removeFriend(UUID uuid) {
        for (Friend friend : FRIENDS) {
            if (friend.getUUID().equals(uuid)) {
                friend.setFriendType(FriendType.NONE);
                return;
            }
        }
    }

    public boolean isFriend(UUID uuid) {
        for (Friend friend : FRIENDS) {
            if (friend.getUUID().equals(uuid))
                return friend.getFriendType() == FriendType.FRIEND
                        || friend.getFriendType() == FriendType.BEST_FRIEND;
        }
        return false;
    }
}
