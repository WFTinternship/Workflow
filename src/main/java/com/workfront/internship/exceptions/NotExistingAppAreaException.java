package com.workfront.internship.exceptions;

/**
 * Created by Karen on 6/2/2017.
 */
public class NotExistingAppAreaException extends RuntimeException {

    // Parameterless Constructor
    public NotExistingAppAreaException() {}

    // Constructor that accepts a message
    public NotExistingAppAreaException(String message)
    {
        super(message);
    }
}
