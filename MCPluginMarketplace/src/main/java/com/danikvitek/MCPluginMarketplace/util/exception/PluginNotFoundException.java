package com.danikvitek.MCPluginMarketplace.util.exception;

public class PluginNotFoundException extends RuntimeException {
    public PluginNotFoundException() {
        super("Plugin not found");
    }
}
