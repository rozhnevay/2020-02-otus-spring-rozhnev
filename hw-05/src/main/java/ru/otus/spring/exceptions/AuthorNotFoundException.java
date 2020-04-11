package ru.otus.spring.exceptions;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
