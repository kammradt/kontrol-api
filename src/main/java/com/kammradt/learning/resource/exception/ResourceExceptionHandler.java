package com.kammradt.learning.resource.exception;

import com.kammradt.learning.domain.exception.ErrorResponse;
import com.kammradt.learning.domain.exception.ErrorResponseList;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.exception.RequestClosedCannotBeUpdatedException;
import com.kammradt.learning.exception.WrongConfirmationPasswordException;
import com.kammradt.learning.service.exception.ExceptionHandlerService;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired ExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), new Date()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage(), new Date()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> duplicateKeyException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), "Duplicated email. Try another one.", new Date()));
    }

    @ExceptionHandler(WrongConfirmationPasswordException.class)
    public ResponseEntity<ErrorResponse> wrongPasswordConfirmationException(WrongConfirmationPasswordException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> SizeFileSizeBiggerThanLimitException(MaxUploadSizeExceededException e) {
        if (exceptionHandlerService.isFileSizeLimitException(e))
            return exceptionHandlerService.handleFileSizeLimitException(e);

        if (exceptionHandlerService.isBulkSizeLimitException(e))
            return exceptionHandlerService.handleBulkSizeLimitException(e);

        return exceptionHandlerService.defaultHandler(e);
    }

    @ExceptionHandler(RequestClosedCannotBeUpdatedException.class)
    public ResponseEntity<ErrorResponse> requestCannotBeUpdatedException(RequestClosedCannotBeUpdatedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage(), new Date()));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());
        ErrorResponseList errorResponseList = new ErrorResponseList(HttpStatus.BAD_REQUEST.value(), "Invalid fields", new Date(), errors);
        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseList);
    }
}
