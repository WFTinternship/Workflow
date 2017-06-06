package com.workfront.internship.workflow.exceptions.dao;

/**
 * Created by Vahag on 6/2/2017.
 */
public class NoSuchUserException extends RuntimeException {

    // Parameterless Constructor
    public NoSuchUserException() {}

    // Constructor that accepts a message
    public NoSuchUserException(String message)
    {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }

    public NoSuchUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
