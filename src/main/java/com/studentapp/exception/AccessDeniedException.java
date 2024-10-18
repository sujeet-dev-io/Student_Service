package com.studentapp.exception;

public class AccessDeniedException extends RuntimeException {

    public String defaultMessage;

    public AccessDeniedException(String defaultMessage) {
        super(defaultMessage);
        this.defaultMessage = defaultMessage;
    }

}
