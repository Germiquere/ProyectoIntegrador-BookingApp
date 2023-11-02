package com.dh.bmn.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException{
    private Integer statusCode;
    public ResourceAlreadyExistsException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
