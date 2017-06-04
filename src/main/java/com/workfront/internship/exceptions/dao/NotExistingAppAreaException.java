package com.workfront.internship.exceptions.dao;

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
}
