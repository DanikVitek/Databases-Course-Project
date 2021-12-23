package com.danikvitek.MCPluginMarketplace.util.exception;

public final class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
