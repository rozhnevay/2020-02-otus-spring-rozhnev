package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author insert(Author author);

    Author getById(long id);

    Author getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
