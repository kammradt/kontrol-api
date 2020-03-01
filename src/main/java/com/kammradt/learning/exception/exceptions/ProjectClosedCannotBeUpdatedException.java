package com.kammradt.learning.exception.exceptions;

public class ProjectClosedCannotBeUpdatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProjectClosedCannotBeUpdatedException(String message) {
        super(message);
    }
}