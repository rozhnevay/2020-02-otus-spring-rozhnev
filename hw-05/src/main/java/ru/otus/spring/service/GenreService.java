package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    void save(Genre genre);

    List<Genre> list();
}
