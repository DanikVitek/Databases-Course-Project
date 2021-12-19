package com.danikvitek.MCPluginMarketplace.util.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
