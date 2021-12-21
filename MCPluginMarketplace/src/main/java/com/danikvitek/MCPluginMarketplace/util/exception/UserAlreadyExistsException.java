package com.danikvitek.MCPluginMarketplace.util.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User with such username/email already exists");
    }
}
