package com.example.realworlddemo.exceptions;

public class UsernameConflictException extends SecurityException{
    public UsernameConflictException(){
        super("Username already exists");
    }
}
