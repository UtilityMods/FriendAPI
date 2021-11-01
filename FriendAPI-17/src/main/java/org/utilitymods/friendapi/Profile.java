package org.utilitymods.friendapi;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public class Profile extends BaseProfile {

    /**
     * Instantiates a new Profile as a friend provided name and uuid of friend.
     *
     * @param playerEntity the PlayerEntity of the friend
     */
    public Profile(@NotNull PlayerEntity playerEntity) {
        super(playerEntity.getGameProfile().getName(), playerEntity.getGameProfile().getId(), Affinity.FRIEND);
    }

    /**
     * Instantiates a new Profile as a friend provided name and uuid of friend.
     *
     * @param playerEntity the PlayerEntity of the friend
     * @param affinity     the affinity with the friend
     */
    public Profile(@NotNull PlayerEntity playerEntity, @NotNull Affinity affinity) {
        super(playerEntity.getGameProfile().getName(), playerEntity.getGameProfile().getId(), affinity);
    }

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param gameProfile the gameprofile of the friend
     */
    public Profile(@NotNull GameProfile gameProfile) {
        super(gameProfile.getName(), gameProfile.getId(), Affinity.FRIEND);
    }

    /**
     * Instantiates a new Profile provided name and uuid of friend.
     *
     * @param gameProfile the gameProfile of the friend
     * @param affinity    the affinity with the friend
     */
    public Profile(@NotNull GameProfile gameProfile, @NotNull Affinity affinity) {
        super(gameProfile.getName(), gameProfile.getId(), affinity);
    }

}
