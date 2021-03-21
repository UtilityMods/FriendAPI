## Universal Friend API

[![Discord](https://img.shields.io/discord/821938163269500938)](https://discord.gg/sK4rK2qCn8)
[![Version](https://img.shields.io/github/v/release/Fabric-Utility-Mods/FriendAPI?label=Version)](https://jitpack.io/#Fabric-Utility-Mods/FriendAPI)

## API Usage
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.Fabric-Utility-Mods:FriendAPI:VERSION'
}
```

### Example Mod
```java
public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        FriendManager.INSTANCE.init();

        addFriends();
    }

    public void addFriends() {
        FriendManager.INSTANCE.addFriend(
                new Profile(
                        "Enemy",
                        UUID.fromString("c7cfce52-16b8-400a-a62d-6bf37f2a093a"),
                        -1L
                )
        );
        FriendManager.INSTANCE.addFriend(
                new Profile(
                        "Neutral",
                        UUID.fromString("23f3185e-e3fd-4105-9d50-5c5caaead461"),
                        0L
                )
        );
        FriendManager.INSTANCE.addFriend(
                new Profile(
                        "Friend",
                        UUID.fromString("822f1836-1f5e-4851-b091-4af9ea3993b4"),
                        1L
                )
        );
    }
}
```