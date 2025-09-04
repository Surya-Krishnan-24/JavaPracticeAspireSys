package com.example.UserService.GlobalExceptionHandler;



public class KeycloakAuthenticationException extends RuntimeException {
    public KeycloakAuthenticationException(String message) {
        super(message);
    }

}
