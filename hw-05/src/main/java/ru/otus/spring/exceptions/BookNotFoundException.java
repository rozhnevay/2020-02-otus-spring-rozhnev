package ru.otus.spring.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String id) {
        super("Книга с id = " + id + " не найдена");
    }
}
