package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;

public interface BookService {
    Book saveBook(String name, String author, String genresStr) throws AuthorNotFoundException;

    Book saveBook(String id, String name, String author, String genresStr) throws BookNotFoundException, AuthorNotFoundException;

    void deleteBook(String id) throws BookNotFoundException;

    String list();

    void addComment(String id, String comment) throws BookNotFoundException;
}
