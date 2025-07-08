package com.example.cocktails.model.exception;

/**
 * Exception thrown when a user is not authenticated but tries to access a protected resource.
 */
public class NotAuthenticatedException extends RuntimeException {
    
    public NotAuthenticatedException() {
        super("User is not authenticated");
    }
    
    public NotAuthenticatedException(String message) {
        super(message);
    }

}
