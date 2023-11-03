package com.dh.bmn.exceptions;

import lombok.Getter;

@Getter
public class TokenExpiredException extends RuntimeException {

    private Integer statusCode;

    public TokenExpiredException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }


}
