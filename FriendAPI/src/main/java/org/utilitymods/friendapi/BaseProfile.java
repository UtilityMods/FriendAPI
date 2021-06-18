package org.utilitymods.friendapi;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

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

    @Override
    public String toString() {
       return "Name: " + name + "\n" + "Uuid: " + uuid+ "\n"  + "Affinity: " + affinity + "\n";
    }

}
