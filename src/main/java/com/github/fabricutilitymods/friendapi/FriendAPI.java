package com.github.fabricutilitymods.friendapi;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FriendAPI implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("FriendAPI");

    public static final FriendManager MANAGER = new FriendManager();

    @Override
    public void onInitialize() {
        long start = System.currentTimeMillis();

        LOGGER.info("Using FriendAPI v1.0");

        MANAGER.load();

        Runtime.getRuntime().addShutdownHook(new Thread(MANAGER::save));

        LOGGER.info("FriendAPI started in " + (System.currentTimeMillis() - start) + "ms");
    }
}
