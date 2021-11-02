package org.utilitymods.friendapi;

public class ProfileData {

    /**
     * The name of the player.
     */
    public final String alias;

    /**
     * The UUID of the player.
     */
    public final Integer kills;

    /**
     * The data of the player.
     */
    public ProfileData(String alias, Integer kills) {
        this.alias = alias;
        this.kills = kills;
    }

    @Override
    public String toString() {
        return "Alias: " + alias + "\n" + "Kills: " + kills + "\n";
    }

}
