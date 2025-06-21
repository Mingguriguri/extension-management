package com.madrascheck.extension_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExtensionLimitExceededException extends RuntimeException {
    public ExtensionLimitExceededException(String message) {
        super(message);
    }
}
