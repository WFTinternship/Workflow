package com.workfront.internship.workflow.exceptions;

/**
 * Created by nane on 6/5/17.
 */
public class InvalidObjectException extends RuntimeException {

    // Parameterless Constructor
    public InvalidObjectException() {}

    // Constructor that accepts a message
    public InvalidObjectException(String message)
    {
        super(message);
    }
}
