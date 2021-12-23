package com.danikvitek.MCPluginMarketplace.util.exception;

public final class PluginNotFoundException extends RuntimeException {
    public PluginNotFoundException() {
        super("Plugin not found");
    }
}
