package com.example.L10minordemo.Exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
    }
}
