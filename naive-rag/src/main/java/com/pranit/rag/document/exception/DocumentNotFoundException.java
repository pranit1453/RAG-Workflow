package com.pranit.rag.document.exception;

import com.pranit.rag.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends BaseException {
    public DocumentNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
