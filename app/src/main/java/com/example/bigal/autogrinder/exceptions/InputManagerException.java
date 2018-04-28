package com.example.bigal.autogrinder.exceptions;

public class InputManagerException extends RuntimeException {

    public InputManagerException(String message, Throwable ex) {
        super(message, ex);
    }

    public InputManagerException(String message) {
        super(message);
    }
}
