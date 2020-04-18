package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface AuthorService {
    String list();

    List<Book> listAuthorBooks(String author);
}
