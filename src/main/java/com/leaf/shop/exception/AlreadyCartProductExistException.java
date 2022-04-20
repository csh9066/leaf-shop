package com.leaf.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyCartProductExistException extends RuntimeException{
    public AlreadyCartProductExistException(String message) {
        super(message);
    }
}
