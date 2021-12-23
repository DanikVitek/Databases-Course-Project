package com.danikvitek.MCPluginMarketplace.util.exception;

public final class PluginAlreadyExistsException extends RuntimeException {
    public PluginAlreadyExistsException(long id) {
        super(String.format("Plugin with such title already exists | /plugins/%d", id));
    }
}
