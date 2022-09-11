package com.bl.fundoonotes.exception;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotesNotFoundException extends RuntimeException{
    private int statusCode;
    private String statusMessage;
    public NotesNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}