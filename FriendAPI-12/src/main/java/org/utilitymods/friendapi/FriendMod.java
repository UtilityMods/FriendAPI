package org.utilitymods.friendapi;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = "friendapi", name = "FriendAPI", version = "1.0.0")
public class FriendMod {

    @Mod.EventHandler
    public void preInit() {
        FriendManager.getInstance().setFactory(ForgeProfileFactory.INSTANCE);
    }
}
