package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

public interface BookService {
    Book saveBook(String name, int authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException;

    Book saveBook(int id, String name, int authorId, String genresStr) throws BookNotFoundException, GenreNotFoundException, AuthorNotFoundException;

    void deleteBook(int id) throws BookNotFoundException;

    String list();
}
