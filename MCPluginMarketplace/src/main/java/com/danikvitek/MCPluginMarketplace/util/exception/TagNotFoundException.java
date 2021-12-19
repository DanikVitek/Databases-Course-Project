package com.danikvitek.MCPluginMarketplace.util.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Tag not found");
    }
}
