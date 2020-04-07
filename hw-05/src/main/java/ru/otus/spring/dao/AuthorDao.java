package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.AuthorNotFoundException;

import java.util.List;

public interface AuthorDao {
    Author save(Author author);

    Author getById(long id) throws AuthorNotFoundException;


    Author getByName(String name) throws AuthorNotFoundException;

    List<Author> getAll();

    void remove(Author author);

    Author getByIdWithBooks(long id) throws AuthorNotFoundException;
}
