package org.utilitymods.friendapi;

import org.jetbrains.annotations.TestOnly;

import java.lang.reflect.Method;
import java.util.UUID;

public class Reflection {

    public static void main(String[] args) {
        try {
            System.out.println("Starting test using just reflection");
            Class<?> c = Class.forName("org.utilitymods.friendapi.FriendManager");
            Method getInstance = c.getMethod("getInstance");
            Method getOnlyAllProfiles = c.getMethod("getOnlyAllProfiles");
            Method isFriend = c.getMethod("isFriend", UUID.class);
            Method getFriend = c.getMethod("getFriend", UUID.class);
            Object api = getInstance.invoke(null);
            System.out.println("Listing all friends");
            System.out.println(getOnlyAllProfiles.invoke(api));
            System.out.println("Checking if friend");
            System.out.println(isFriend.invoke(api, UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306")));
            System.out.println("Getting friend profile");
            System.out.println(getFriend.invoke(api, UUID.fromString("d738f0f0-85e4-4145-b735-3bc77396f306")));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
