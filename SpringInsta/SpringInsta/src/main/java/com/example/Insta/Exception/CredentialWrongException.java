package com.example.Insta.Exception;

public class CredentialWrongException extends RuntimeException {
    public CredentialWrongException(String msg){
        super(msg);
    }
}
