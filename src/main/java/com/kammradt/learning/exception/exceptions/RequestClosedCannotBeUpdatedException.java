package com.kammradt.learning.exception.exceptions;

public class RequestClosedCannotBeUpdatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestClosedCannotBeUpdatedException(String message) {
        super(message);
    }
}