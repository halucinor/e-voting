package com.gabia.evoting.web;

import com.gabia.evoting.exception.EmailDuplicationException;
import com.gabia.evoting.web.dto.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    protected ResponseEntity<ErrorResponse> ClientException(Exception exception) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("404");
        error.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailDuplicationException.class)
    protected ResponseEntity<ErrorResponse> DuplicateKey(Exception exception) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("409");
        error.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
