package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookNotFoundException;

import java.util.List;

public interface BookDao {

    Book save(Book book);

    Book getById(long id) throws BookNotFoundException;

    List<Book> getAll();

    void remove(Book book);

}
