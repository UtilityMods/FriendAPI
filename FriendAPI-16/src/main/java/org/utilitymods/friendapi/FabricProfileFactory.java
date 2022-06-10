package org.utilitymods.friendapi;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.utilitymods.friendapi.profiles.Affinity;
import org.utilitymods.friendapi.profiles.Profile;
import org.utilitymods.friendapi.profiles.ProfileFactory;

/**
 * A factory for creating {@link Profile}s.
 * Allows you to use the raw player object to create a profile.
 */
public final class FabricProfileFactory extends ProfileFactory {

    /**
     * Singleton instance of the factory.
     */
    public static final FabricProfileFactory INSTANCE = new FabricProfileFactory();

    public Profile createProfile(@NotNull GameProfile profile, @NotNull Affinity affinity) {
        return createProfile(profile.getName(), profile.getId(), affinity);
    }

    public Profile createProfile(@NotNull GameProfile profile) {
        return createProfile(profile, Affinity.NEUTRAL);
    }

    public Profile createFriend(@NotNull GameProfile profile) {
        return createProfile(profile, Affinity.FRIEND);
    }

    public Profile createEnemy(@NotNull GameProfile profile) {
        return createProfile(profile, Affinity.ENEMY);
    }

    public Profile createProfile(@NotNull PlayerEntity player, @NotNull Affinity affinity) {
        return createProfile(player.getGameProfile(), affinity);
    }

    public Profile createProfile(@NotNull PlayerEntity player) {
        return createProfile(player.getGameProfile());
    }

    public Profile createFriend(@NotNull PlayerEntity player) {
        return createFriend(player.getGameProfile());
    }

    public Profile createEnemy(@NotNull PlayerEntity player) {
        return createEnemy(player.getGameProfile());
    }

}
