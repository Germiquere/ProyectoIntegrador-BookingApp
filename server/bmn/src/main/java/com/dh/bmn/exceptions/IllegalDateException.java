package com.dh.bmn.exceptions;

import lombok.Getter;

@Getter
public class IllegalDateException extends RuntimeException{
    private Integer statusCode;

    public IllegalDateException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
