package com.danikvitek.MCPluginMarketplace.util.exception;

public final class UserIsNotBannedException extends RuntimeException {
    public UserIsNotBannedException() {
        super("User is not banned");
    }
}
