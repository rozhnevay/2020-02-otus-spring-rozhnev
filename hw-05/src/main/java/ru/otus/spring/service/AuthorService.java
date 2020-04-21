package ru.otus.spring.service;

import ru.otus.spring.exceptions.AuthorNotFoundException;

public interface AuthorService {
    String list();

    String insertAuthor(String author);

    String listAuthorBooks(String author) throws AuthorNotFoundException;
}
