package org.utilitymods.friendapi.profiles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.utilitymods.friendapi.exceptions.ApiFailedException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ProfileFactory {

    public Profile createProfile(@NotNull String username, @NotNull UUID uuid, @NotNull Affinity affinity) {
        return new Profile(username, uuid, affinity);
    }

    public Profile createProfile(@NotNull String username, @NotNull UUID uuid) {
        return createProfile(username, uuid, Affinity.NEUTRAL);
    }

    public Profile createFriend(@NotNull String username, @NotNull UUID uuid) {
        return createProfile(username, uuid, Affinity.FRIEND);
    }

    public Profile createEnemy(@NotNull String username, @NotNull UUID uuid) {
        return createProfile(username, uuid, Affinity.ENEMY);
    }

    /**
     * Attempts to make a new base profile though getting the name from the mojang api
     *
     * @param uuid     Uuid of the player you want a profile of
     * @param affinity the affinity with the profile
     * @return new BaseProfile based on the data from the api
     * @throws ApiFailedException if some part of the process fails throw an error
     */
    public Profile createProfile(@NotNull UUID uuid, @NotNull Affinity affinity) throws ApiFailedException {
        try {
            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JsonArray jsonArray = new JsonParser().parse(new InputStreamReader(conn.getInputStream())).getAsJsonArray();
            String name = jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("name").getAsString();
            return createProfile(name, uuid, affinity);
        } catch (Exception e) {
            throw new ApiFailedException("no username associated with uuid:" + uuid);
        }
    }

    /**
     * Attempts to make a new neutral profile though getting the name from the mojang api
     * @param uuid Uuid of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when uuid is invalid
     */
    public Profile createProfile(@NotNull UUID uuid) throws ApiFailedException {
        return createProfile(uuid, Affinity.NEUTRAL);
    }

    /**
     * Attempts to make a new friend profile though getting the name from the mojang api
     * @param uuid Uuid of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createFriend(@NotNull UUID uuid) throws ApiFailedException {
        return createProfile(uuid, Affinity.FRIEND);
    }

    /**
     * Attempts to make a new enemy profile though getting the name from the mojang api
     * @param uuid Uuid of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createEnemy(@NotNull UUID uuid) throws ApiFailedException {
        return createProfile(uuid, Affinity.ENEMY);
    }

    /**
     * Attempts to make a new profile though getting the uuid from the mojang api
     * @param username Username of the player you want a profile of
     * @param affinity the affinity with the profile
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createProfile(@NotNull String username, @NotNull Affinity affinity) throws ApiFailedException {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
            UUID uuid = UUID.fromString(jsonObject.get("id").getAsString().replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
            String name = jsonObject.get("name").getAsString();
            return createProfile(name, uuid, affinity);
        } catch (Exception e) {
            throw new ApiFailedException("no uuid associated with " + username);
        }
    }

    /**
     * Attempts to make a new neutral profile though getting the uuid from the mojang api
     * @param username Username of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createProfile(@NotNull String username) throws ApiFailedException {
        return createProfile(username, Affinity.NEUTRAL);
    }

    /**
     * Attempts to make a new friend profile though getting the uuid from the mojang api
     * @param username Username of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createFriend(@NotNull String username) throws ApiFailedException {
        return createProfile(username, Affinity.FRIEND);
    }

    /**
     * Attempts to make a new enemy profile though getting the uuid from the mojang api
     * @param username Username of the player you want a profile of
     * @return new Profile with name and uuid based on the data from the api
     * @throws ApiFailedException when name is invalid
     */
    public Profile createEnemy(@NotNull String username) throws ApiFailedException {
        return createProfile(username, Affinity.ENEMY);
    }

}
