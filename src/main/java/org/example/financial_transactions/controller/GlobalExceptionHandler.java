package org.example.financial_transactions.controller;

import org.example.financial_transactions.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleException(DuplicateException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleException(AccountNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "Total Errors:" + ex.getErrorCount() + " First Error:" + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleException(InsufficientFundsException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity<?> handleException(DisabledAccountException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<?> handleException(DuplicateAccountException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleException(TransactionNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handleException(IdNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }


}
