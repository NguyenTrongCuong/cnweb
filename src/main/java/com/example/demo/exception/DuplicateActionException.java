package com.example.demo.exception;

public class DuplicateActionException extends Exception {

    public DuplicateActionException() {
    }

    public DuplicateActionException(String message) {
        super(message);
    }
}
