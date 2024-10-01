package com.yuzarsif.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}
