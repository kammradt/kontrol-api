package com.kammradt.learning.service.exception;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.kammradt.learning.domain.exception.ErrorResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@Service
public class ExceptionHandlerService {
    public boolean isFileSizeLimitException(MaxUploadSizeExceededException e) {
        return FileUploadBase.FileSizeLimitExceededException.class == e.getRootCause().getClass();
    }

    public ResponseEntity<ErrorResponse> handleFileSizeLimitException(MaxUploadSizeExceededException e) {
        String msg = e.getMessage().split(":")[2].trim();

        String maximumSize = msg.split(" ")[9];
        String maximumSizeFormatted = convertBytesToMBs(removeParentheses(maximumSize));
        msg = msg.replace(maximumSize, maximumSizeFormatted);
        msg = msg.replace("bytes", "MBs");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), msg, new Date()));
    }

    public boolean isBulkSizeLimitException(MaxUploadSizeExceededException e) {
        return FileUploadBase.SizeLimitExceededException.class == e.getRootCause().getClass();
    }

    public ResponseEntity<ErrorResponse> handleBulkSizeLimitException(MaxUploadSizeExceededException e) {
        String msg = e.getMessage().split(":")[2].trim();

        String size = msg.split(" ")[7];
        String sizeFormatted = convertBytesToMBs(removeParentheses(size));
        msg = msg.replace(size, sizeFormatted.substring(0, 4) + " MBs");

        String maximumSize = msg.split(" ")[13];
        String maximumSizeFormatted = convertBytesToMBs(removeParentheses(maximumSize));
        msg = msg.replace(maximumSize, maximumSizeFormatted.substring(0, 3) + " MBs");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), msg, new Date()));
    }

    public String removeParentheses(String text) {
        return text.replace("(", "").replace(")", "");
    }

    public String convertBytesToMBs(String sizeInBytes) {
        long BYTES_TO_MBS = 1024 * 1024;
        Double valueInMbs = Double.parseDouble(sizeInBytes) / BYTES_TO_MBS;
        return valueInMbs.toString();
    }

    public ResponseEntity<ErrorResponse> defaultHandler(MaxUploadSizeExceededException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date()));
    }
}
