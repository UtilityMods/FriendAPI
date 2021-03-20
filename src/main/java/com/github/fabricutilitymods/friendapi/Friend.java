package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;

public final class Friend {

    public GameProfile gameProfile;
    public Long affinity;

    public Friend(@NotNull GameProfile gameProfile, @NotNull Long affinity) {
        this.gameProfile = gameProfile;
        this.affinity = affinity;
    }

    public static Friend getEmpty() {
        return new Friend(new GameProfile(null, ""), 0L);
    }
}
