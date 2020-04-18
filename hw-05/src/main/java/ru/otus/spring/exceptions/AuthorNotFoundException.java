package ru.otus.spring.exceptions;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(long id) {
        super("Автор с id = " + id + " не найден");
    }

    public AuthorNotFoundException(String name) {
        super("Автор " + name + " не найден");
    }
}
