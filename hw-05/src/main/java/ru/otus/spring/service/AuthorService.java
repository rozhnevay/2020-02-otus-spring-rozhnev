package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {
    void save(Author author);

    List<Author> list();

    List<Book> listAuthorBooks(long authorId) throws AuthorNotFoundException;
}
