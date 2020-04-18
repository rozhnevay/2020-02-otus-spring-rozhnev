package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

public interface BookService {
    Book saveBook(String name, long authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException;

    Book saveBook(long id, String name, long authorId, String genresStr) throws BookNotFoundException, GenreNotFoundException, AuthorNotFoundException;

    void deleteBook(long id) throws BookNotFoundException;

    String list();
}
