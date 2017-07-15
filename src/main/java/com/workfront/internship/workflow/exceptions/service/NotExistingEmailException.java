package com.workfront.internship.workflow.exceptions.service;

/**
 * Created by Vahag on 7/14/2017
 */
public class NotExistingEmailException extends RuntimeException {
    public NotExistingEmailException() {
        super();
    }

    public NotExistingEmailException(String message) {
        super(message);
    }

    public NotExistingEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistingEmailException(Throwable cause) {
        super(cause);
    }

    protected NotExistingEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
