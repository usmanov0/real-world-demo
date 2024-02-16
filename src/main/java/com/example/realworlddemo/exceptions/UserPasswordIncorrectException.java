package com.example.realworlddemo.exceptions;

public class UserPasswordIncorrectException extends SecurityException{
    public UserPasswordIncorrectException(){
        super("Invalid password");
    }
}
