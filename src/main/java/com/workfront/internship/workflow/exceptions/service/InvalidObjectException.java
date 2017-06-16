package com.workfront.internship.workflow.exceptions.service;

/**
 * Created by nane on 6/5/17
 */
public class InvalidObjectException extends RuntimeException {

    // Parameterless Constructor
    public InvalidObjectException() {}

    // Constructor that accepts a message
    public InvalidObjectException(String message)
    {
        super(message);
    }

    public InvalidObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidObjectException(Throwable cause) {
        super(cause);
    }

    public InvalidObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
