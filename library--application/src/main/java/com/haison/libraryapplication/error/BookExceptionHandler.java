package com.haison.libraryapplication.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BookResponseMessage> exceptionHandler(BookExceptionNotFound exc, WebRequest webRequest) {
        BookResponseMessage response = new BookResponseMessage();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exc.getMessage());
        response.setPath(webRequest.getDescription(false));
        response.setTimeStamp(LocalDateTime.now());

        return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
