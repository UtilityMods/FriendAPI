package com.github.fabricutilitymods.friendapi;

public enum FriendType {
    ENEMY(-1),
    NEUTRAL(0),
    FRIEND(1),
    BEST_FRIEND(2);
        
    private final int priority;
    
    FriendType(int priority) {
        this.priority = priority;
    }
}
