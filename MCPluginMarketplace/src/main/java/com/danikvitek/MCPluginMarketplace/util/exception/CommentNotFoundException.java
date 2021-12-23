package com.danikvitek.MCPluginMarketplace.util.exception;

public final class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("Comment not found");
    }
}
