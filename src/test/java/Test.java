import org.utilitymods.friendapi.BaseProfile;
import org.utilitymods.friendapi.FriendManager;

import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        FriendManager friendManager = FriendManager.INSTANCE;
        friendManager.addFriend(new BaseProfile("Moo", UUID.randomUUID()));
        System.out.println(friendManager.getFriendMapCopy());
    }
}
