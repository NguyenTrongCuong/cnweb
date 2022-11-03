package com.example.demo.exception;

public class ExistedResourceException extends Exception {

    public ExistedResourceException() {
    }

    public ExistedResourceException(String message) {
        super(message);
    }
}
