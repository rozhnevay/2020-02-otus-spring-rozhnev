package ru.otus.spring.exceptions;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(String name) {
        super("Жанр " + name + " не найден");
    }
}
