package com.pranit.rag.document.exception;

import com.pranit.rag.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DocumentProcessingException extends BaseException {
    public DocumentProcessingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
