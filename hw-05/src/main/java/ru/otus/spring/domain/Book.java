package ru.otus.spring.domain;

import lombok.Data;

import java.util.Set;

@Data
public class Book {
    private long id;
    private final String name;
    private Author author;
    private Set<Genre> genreSet;

    public Book(String name, Author author, Set<Genre> genreSet) {
        this.name = name;
        this.author = author;
        this.genreSet = genreSet;
    }

    public Book(long id, String name, Author author, Set<Genre> genreSet) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genreSet = genreSet;
    }

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(String name) {
        this.name = name;
    }
}
