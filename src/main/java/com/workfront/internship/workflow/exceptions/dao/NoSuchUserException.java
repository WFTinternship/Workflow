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
}
