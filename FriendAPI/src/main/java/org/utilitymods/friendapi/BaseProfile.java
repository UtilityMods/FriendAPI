package org.utilitymods.friendapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Profile class to store attributes of a friend.
 */
public class BaseProfile {

    /**
     * The name of the Player
     */
    public String name;

    /**
     * The uuid of the Player
     */
    public UUID uuid;

    /**
     * Your affinity to the Player
     */
    public Affinity affinity;

    /**
     * Custom data for the Profile
     */
    @SerializedName("data")
    protected Map<String, Object> dataMap;

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param name     the name of the friend
     * @param uuid     the uuid of the friend
     * @param affinity the affinity with the friend
     */
    public BaseProfile(@NotNull String name, @NotNull UUID uuid, @NotNull Affinity affinity) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = affinity;
    }

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param name     the name of the friend
     * @param uuid     the uuid of the friend
     */
    public BaseProfile(@NotNull String name, @NotNull UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = Affinity.FRIEND;
    }

    public boolean addData(String key, Object data) {
        if (dataMap == null) {
            dataMap = new ConcurrentHashMap<>();
        }
        if (dataMap.containsKey(key)) {
            return false;
        }
        dataMap.compute(key, ((key1, data1) -> data1 = data));
        return true;
    }

    public Object getData(String key) {
        if (dataMap == null) {
            return null;
        }
        return dataMap.get(key);
    }

    /**
     * Attempts to make a new base profile though getting the uuid from the mojang api
     * @param username Username of the player you want a profile of
     * @param affinity the affinity with the profile
     * @return new BaseProfile based on the data from the api
     * @throws ApiFailedException if some part of the process fails throw an error
     */
    public static BaseProfile fromUsername(String username, Affinity affinity) throws ApiFailedException {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JsonObject jsonObject = new JsonParser().parse(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
            UUID uuid = UUID.fromString(jsonObject.get("id").getAsString().replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
            String name = jsonObject.get("name").getAsString();
            return new BaseProfile(name, uuid, affinity);
        } catch (Exception e) {
            throw new ApiFailedException("no uuid associated with " + username);
        }
    }

    /**
     * Attempts to make a new base profile though getting the name from the mojang api
     * @param uuid Uuid of the player you want a profile of
     * @param affinity the affinity with the profile
     * @return new BaseProfile based on the data from the api
     * @throws ApiFailedException if some part of the process fails throw an error
     */
    public static BaseProfile fromUuid(UUID uuid, Affinity affinity) throws ApiFailedException {
        try {
            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.toString() + "/names");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JsonArray jsonArray = new JsonParser().parse(new InputStreamReader(conn.getInputStream())).getAsJsonArray();
            String name = jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("name").getAsString();
            return new BaseProfile(name, uuid, affinity);
        } catch (Exception e) {
            throw new ApiFailedException("no username associated with uuid:" + uuid);
        }
    }

    @Override
    public String toString() {
       return "Name: " + name + "\n" + "Uuid: " + uuid+ "\n"  + "Affinity: " + affinity + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        BaseProfile baseProfile = (BaseProfile) obj;

        if (name == null) {
            if (baseProfile.name != null) {
                return false;
            }
        } else if (!name.equals(baseProfile.name)) {
            return false;
        }

        if (uuid == null) {
            if (baseProfile.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(baseProfile.uuid)) {
            return false;
        }

        if (affinity == null) {
            if (baseProfile.affinity != null) {
                return false;
            }
        } else if (!affinity.equals(baseProfile.affinity)) {
            return false;
        }

        return true;
    }

}
