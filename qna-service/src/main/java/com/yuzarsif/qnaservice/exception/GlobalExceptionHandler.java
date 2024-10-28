package com.yuzarsif.qnaservice.exception;

import com.yuzarsif.contextshare.productservice.exception.AlreadyExistsException;
import com.yuzarsif.contextshare.productservice.exception.ClientException;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.exception.ErrorDetails;
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

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                "NOT_FOUND_ERROR",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetails);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleAlreadyExistsException(AlreadyExistsException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                "ALREADY_EXISTS_ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDetails);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorDetails> handleEntityClientException(ClientException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                "CLIENT_ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetails);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                "VALIDATION_ERROR",
                ex.getBindingResult().getFieldErrors().get(0).getField() + ": " + ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);
    }
}