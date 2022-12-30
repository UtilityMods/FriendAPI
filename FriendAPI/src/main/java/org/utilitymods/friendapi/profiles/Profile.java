package org.utilitymods.friendapi.profiles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.utilitymods.friendapi.exceptions.ApiFailedException;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Profile class to store attributes of a friend.
 */
public class Profile implements Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the Player
     */
    @SerializedName("name")
    private String name;

    /**
     * The uuid of the Player
     */
    @SerializedName("uuid")
    private final UUID uuid;

    /**
     * Your affinity to the Player
     */
    @SerializedName("affinity")
    private Affinity affinity;

    /**
     * Custom data for the Profile
     */
    @SerializedName("data")
    protected Map<String, Object> dataMap = new ConcurrentHashMap<>();

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param name     the name of the friend
     * @param uuid     the uuid of the friend
     * @param affinity the affinity with the friend
     */
    public Profile(@NotNull String name, @NotNull UUID uuid, @NotNull Affinity affinity) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = affinity;
    }

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param name the name of the friend
     * @param uuid the uuid of the friend
     */
    public Profile(@NotNull String name, @NotNull UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = Affinity.FRIEND;
    }

    /**
     * Add custom data to the profile
     *
     * @param key  The key its place in the map with.
     *             Should follow a naming scheme of the client's name an underscore and the object's name.
     *             Ex: WURST_ALIAS
     * @param data the dataObject you want to save
     */
    //TODO: Add a way to remove data
    public Object editData(String key, Object data) {
        return dataMap.compute(key, ((key1, data1) -> data1 = data));
    }

    /**
     * Gets data already in the list
     *
     * @param key The key the data is under
     * @return The object associated with the key
     */
    public Object getData(String key) {
        return dataMap.get(key);
    }

    /**
     * Gets the name of the profile.
     *
     * @return The name of the profile.
     */
    public String getName() {
        return name;
    }

    /**
     * Set's the name of the profile.
     *
     * @param name The name of the profile.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get's the uuid of the profile.
     *
     * @return The uuid of the profile.
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Get's the affinity of the profile.
     *
     * @return The affinity of the profile.
     */
    public Affinity getAffinity() {
        return affinity;
    }

    /**
     * Set's the affinity of the profile.
     *
     * @param affinity The affinity of the profile.
     */
    public void setAffinity(Affinity affinity) {
        this.affinity = affinity;
    }

    /**
     * Is the profile a friend?
     */
    public boolean isFriend() {
        return getAffinity() == Affinity.FRIEND;
    }

    /**
     * Is the profile an enemy?
     */
    public boolean isEnemy() {
        return getAffinity() == Affinity.ENEMY;
    }

    /**
     * Is the profile neutral?
     */
    public boolean isNeutral() {
        return getAffinity() == Affinity.NEUTRAL;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + "\n" + "Uuid: " + uuid + "\n" + "Affinity: " + affinity + "\n" + "Data: " + dataMap;
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

        Profile profile = (Profile) obj;

        if (name == null) {
            if (profile.name != null) {
                return false;
            }
        } else if (!name.equals(profile.name)) {
            return false;
        }

        if (uuid == null) {
            if (profile.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(profile.uuid)) {
            return false;
        }

        if (affinity == null) {
            return profile.affinity == null;
        } else return affinity.equals(profile.affinity);
    }

}
