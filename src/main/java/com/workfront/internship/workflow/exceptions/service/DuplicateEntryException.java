package com.workfront.internship.workflow.exceptions.service;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by Vahag on 6/6/2017.
 */
public class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntryException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
