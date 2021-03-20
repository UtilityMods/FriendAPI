package com.github.fabricutilitymods.friendapi;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Profile class to store attributes of a friend.
 */
public final class Profile {

    public String name;
    public UUID uuid;
    public Long affinity;

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param name     the name of the friend
     * @param uuid     the uuid of the friend
     * @param affinity the affinity with the friend
     */
    public Profile(@NotNull String name, @NotNull UUID uuid, @NotNull Long affinity) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = affinity;
    }

    /**
     * Instantiates a new Profile provided the GameProfile of the friend.
     *
     * @param profile  the GameProfile of the friend
     * @param affinity the affinity with the friend
     */
    public Profile(@NotNull GameProfile profile, @NotNull Long affinity) {
        this.name = profile.getName();
        this.uuid = profile.getId();
        this.affinity = affinity;
    }

    /**
     * Instantiates a new Profile provided the PlayerEntity of the friend.
     *
     * @param friendEntity the PlayerEntity of the friend
     * @param affinity     the affinity with the friend
     */
    public Profile(@NotNull PlayerEntity friendEntity, @NotNull Long affinity) {
        this.name = friendEntity.getGameProfile().getName();
        this.uuid = friendEntity.getGameProfile().getId();
        this.affinity = affinity;
    }
}
