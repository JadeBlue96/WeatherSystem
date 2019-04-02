package com.isoft.rest.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final static Logger logger = Logger.getLogger(ResourceNotFoundException.class.getName());
    
    
    public ResourceNotFoundException(String message) {
        super(message);
        logger.log(Level.SEVERE, message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.log(Level.SEVERE, message);
    }
}
