package com.haison.libraryapplication.error;

public class BookExceptionNotFound extends RuntimeException{

    public BookExceptionNotFound(String message) {
        super(message);
    }

}
