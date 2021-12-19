package com.danikvitek.MCPluginMarketplace.util.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(long id) {
        super(String.format("Tag with such title already exists | /tags/%d", id));
    }
}
