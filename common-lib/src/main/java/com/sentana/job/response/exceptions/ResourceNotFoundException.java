package com.sentana.job.response.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
