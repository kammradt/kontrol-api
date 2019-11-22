package com.kammradt.learning.domain.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class ErrorResponseList extends ErrorResponse {

    private static final long serialVersionUID = 1L;

    private List<String> errors;

    public ErrorResponseList(int code, String message, Date date, List<String> errors) {
        super(code, message, date);
        this.errors = errors;
    }
}
