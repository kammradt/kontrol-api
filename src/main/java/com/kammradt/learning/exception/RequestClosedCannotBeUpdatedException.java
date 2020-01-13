package com.kammradt.learning.exception;

public class RequestClosedCannotBeUpdatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestClosedCannotBeUpdatedException(String message) {
        super(message);
    }
}