package com.github.fabricutilitymods.friendapi;

import com.google.gson.annotations.SerializedName;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Profile class to store attributes of a friend.
 */
public final class Profile {

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
    public Profile(@NotNull String name, @NotNull UUID uuid, @NotNull Affinity affinity) {
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
    public Profile(@NotNull GameProfile profile, @NotNull Affinity affinity) {
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
    public Profile(@NotNull PlayerEntity friendEntity, @NotNull Affinity affinity) {
        this.name = friendEntity.getGameProfile().getName();
        this.uuid = friendEntity.getGameProfile().getId();
        this.affinity = affinity;
    }

    /**
     * Instantiates a new Profile as a friend provided name and uuid of friend.
     *
     * @param name     the name of the friend
     * @param uuid     the uuid of the friend
     */
    public Profile(@NotNull String name, @NotNull UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.affinity = Affinity.FRIEND;
    }

    /**
     * Instantiates a new Profile as a friend provided name and uuid of friend.
     *
     * @param profile  the GameProfile of the friend
     */
    public Profile(@NotNull GameProfile profile) {
        this.name = profile.getName();
        this.uuid = profile.getId();
        this.affinity = Affinity.FRIEND;
    }

    /**
     * Instantiates a new Profile as a friend provided name and uuid of friend.
     *
     * @param friendEntity the PlayerEntity of the friend
     */
    public Profile(@NotNull PlayerEntity friendEntity) {
        this.name = friendEntity.getGameProfile().getName();
        this.uuid = friendEntity.getGameProfile().getId();
        this.affinity = Affinity.FRIEND;
    }

}
