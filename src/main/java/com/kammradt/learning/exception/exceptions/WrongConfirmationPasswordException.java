package com.kammradt.learning.exception.exceptions;

public class WrongConfirmationPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongConfirmationPasswordException(String message) {
        super(message);
    }


}
