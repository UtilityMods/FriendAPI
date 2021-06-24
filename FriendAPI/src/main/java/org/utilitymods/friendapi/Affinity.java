package org.utilitymods.friendapi;

/**
 * Enum used to define affiliation with the friend
 */
public enum Affinity {

    FRIEND(0),

    NEUTRAL(1),

    ENEMY(2);

    public final int type;

    Affinity(int type) {
        this.type = type;
    }

}
