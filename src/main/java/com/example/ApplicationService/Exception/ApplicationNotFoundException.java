package com.example.ApplicationService.Exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException(
            String message) {

        super(message);
    }
}
