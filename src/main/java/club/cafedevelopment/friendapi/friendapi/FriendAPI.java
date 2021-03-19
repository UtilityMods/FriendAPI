package club.cafedevelopment.friendapi.friendapi;

import net.fabricmc.api.ModInitializer;

/**
 * @author yagel15637
 */
public final class FriendAPI implements ModInitializer {
    public FriendAPI() {
        FriendManager.INSTANCE.init();
    }

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(FriendManager.INSTANCE::save));
    }
}
