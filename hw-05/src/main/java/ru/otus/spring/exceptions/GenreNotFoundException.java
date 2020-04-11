package ru.otus.spring.exceptions;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
