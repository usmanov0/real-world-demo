package com.example.realworlddemo.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("No such user found");
    }
}
