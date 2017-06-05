package com.workfront.internship.workflow.exceptions.dao;

/**
 * Created by Vahag on 6/2/2017.
 */
public class NotExistingAppAreaException extends RuntimeException {

    // Parameterless Constructor
    public NotExistingAppAreaException() {}

    // Constructor that accepts a message
    public NotExistingAppAreaException(String message)
    {
        super(message);
    }

    public NotExistingAppAreaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistingAppAreaException(Throwable cause) {
        super(cause);
    }

    public NotExistingAppAreaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
