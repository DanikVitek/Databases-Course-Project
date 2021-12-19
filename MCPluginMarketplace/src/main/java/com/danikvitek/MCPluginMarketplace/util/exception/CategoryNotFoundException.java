package com.danikvitek.MCPluginMarketplace.util.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
