package com.madrascheck.extension_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateExtensionException extends RuntimeException {
    public DuplicateExtensionException(String message) {
        super(message);
    }
}
