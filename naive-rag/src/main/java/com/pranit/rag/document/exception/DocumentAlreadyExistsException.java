package com.pranit.rag.document.exception;

import com.pranit.rag.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DocumentAlreadyExistsException extends BaseException {
    public DocumentAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
