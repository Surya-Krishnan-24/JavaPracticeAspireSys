package com.example.Insta.Exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String msg){
        super(msg);
    }
}
