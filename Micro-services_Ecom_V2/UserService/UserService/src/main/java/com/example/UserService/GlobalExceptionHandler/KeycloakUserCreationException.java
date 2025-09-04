package com.example.UserService.GlobalExceptionHandler;

public class KeycloakUserCreationException extends RuntimeException {
    public KeycloakUserCreationException(String message) {
        super(message);
    }
}
