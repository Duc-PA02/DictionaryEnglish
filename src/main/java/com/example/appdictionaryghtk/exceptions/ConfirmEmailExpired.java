package com.example.appdictionaryghtk.exceptions;

public class ConfirmEmailExpired extends RuntimeException{
    public ConfirmEmailExpired(String message) {
        super(message);
    }
}
