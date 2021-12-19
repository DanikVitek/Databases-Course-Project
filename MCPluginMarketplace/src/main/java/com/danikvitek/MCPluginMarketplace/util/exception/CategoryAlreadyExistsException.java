package com.danikvitek.MCPluginMarketplace.util.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(int id) {
        super(String.format("Category with such title already exists | /categories/%d", id));
    }
}
