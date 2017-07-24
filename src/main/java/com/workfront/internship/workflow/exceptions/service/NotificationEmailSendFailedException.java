package com.workfront.internship.workflow.exceptions.service;

/**
 * Created by Vahag on 7/24/2017
 */
public class NotificationEmailSendFailedException extends RuntimeException {

    public NotificationEmailSendFailedException() {
        super();
    }

    public NotificationEmailSendFailedException(String message) {
        super(message);
    }

    public NotificationEmailSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationEmailSendFailedException(Throwable cause) {
        super(cause);
    }

    protected NotificationEmailSendFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
