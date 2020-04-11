package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.GenreNotFoundException;

import java.util.List;

public interface GenreDao {
    Genre save(Genre genre);

    Genre getById(long id);

    Genre getByName(String name) throws GenreNotFoundException;

    List<Genre> getAll();

    void remove(Genre genre);
}
