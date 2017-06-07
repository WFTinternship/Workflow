package com.workfront.internship.workflow.exceptions.dao;

/**
 * Created by nane on 6/5/17
 */
public class NoRowsAffectedException extends RuntimeException {

    // Parameterless Constructor
    public NoRowsAffectedException() {}

    // Constructor that accepts a message
    public NoRowsAffectedException(String message)
    {
        super(message);
    }
}
