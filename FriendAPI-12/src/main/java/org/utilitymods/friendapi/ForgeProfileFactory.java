package org.utilitymods.friendapi;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.utilitymods.friendapi.profiles.Affinity;
import org.utilitymods.friendapi.profiles.Profile;
import org.utilitymods.friendapi.profiles.ProfileFactory;

/**
 * A factory for creating {@link Profile}s.
 * Allows you to use the raw player object to create a profile.
 */
public final class ForgeProfileFactory extends ProfileFactory {

    /**
     * Singleton instance of the factory.
     */
    public static final ForgeProfileFactory INSTANCE = new ForgeProfileFactory();

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

    public Profile createProfile(@NotNull EntityPlayer player, @NotNull Affinity affinity) {
        return createProfile(player.getGameProfile(), affinity);
    }

    public Profile createProfile(@NotNull EntityPlayer player) {
        return createProfile(player.getGameProfile());
    }

    public Profile createFriend(@NotNull EntityPlayer player) {
        return createFriend(player.getGameProfile());
    }

    public Profile createEnemy(@NotNull EntityPlayer player) {
        return createEnemy(player.getGameProfile());
    }

}
