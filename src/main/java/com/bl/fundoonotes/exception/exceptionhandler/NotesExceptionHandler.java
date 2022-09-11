package com.bl.fundoonotes.exception.exceptionhandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.bl.fundoonotes.exception.NotesNotFoundException;
import com.bl.fundoonotes.util.Response;

@ControllerAdvice
public class NotesExceptionHandler {
    @ExceptionHandler(NotesNotFoundException.class)
    public ResponseEntity<Response> handleHiringException(NotesNotFoundException he){
        Response response=new Response();
        response.setErrorCode(400);
        response.setMessage(he.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}