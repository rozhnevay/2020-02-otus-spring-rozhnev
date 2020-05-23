package ru.otus.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

public interface BookService {
    Book save(Book book) throws GenreNotFoundException, AuthorNotFoundException;

    void deleteBook(long id) throws BookNotFoundException;

    Page<Book> list(Pageable pageRequest);
}
