package com.danikvitek.MCPluginMarketplace.util.exception;

public final class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Tag not found");
    }
}
