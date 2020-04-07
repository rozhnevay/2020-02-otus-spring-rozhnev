package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.GenreNotFoundException;

import java.util.Set;

public interface GenreService {
    Set<Genre> getGenresSetByString(String genresStr) throws GenreNotFoundException;

    Genre insertGenre(String name);

    String list();
}
