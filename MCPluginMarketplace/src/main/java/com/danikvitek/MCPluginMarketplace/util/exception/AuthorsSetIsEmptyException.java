package com.danikvitek.MCPluginMarketplace.util.exception;

public class AuthorsSetIsEmptyException extends IllegalArgumentException {
    public static final String message = "The set of authors for the new plugin is empty or null"; 
    
    public AuthorsSetIsEmptyException() {
        super(message);
    }
}
