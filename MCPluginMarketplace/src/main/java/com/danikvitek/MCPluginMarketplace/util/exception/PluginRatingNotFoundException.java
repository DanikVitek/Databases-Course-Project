package com.danikvitek.MCPluginMarketplace.util.exception;

public final class PluginRatingNotFoundException extends RuntimeException {
    public PluginRatingNotFoundException() {
        super("Plugin rating not found");
    }
}
