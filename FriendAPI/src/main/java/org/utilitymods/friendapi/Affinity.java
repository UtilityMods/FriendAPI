package org.utilitymods.friendapi;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

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

    /**
     * Gets the affinity from the given string
     *
     * @param affinity The string to get the affinity from
     * @return The affinity
     */
    @Nullable
    public static Affinity byName(@Nullable String name) {
        return name == null ? null : Affinity.valueOf(name.toUpperCase(Locale.ROOT));
    }

}
