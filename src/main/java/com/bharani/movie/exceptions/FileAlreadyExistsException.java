package com.bharani.movie.exceptions;

public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
