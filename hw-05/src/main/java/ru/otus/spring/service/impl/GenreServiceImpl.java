package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.service.GenreService;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public Set<Genre> getGenresSetByString(String genresStr) throws GenreNotFoundException {
        String[] genreListStr = genresStr.split(",");
        Set<Genre> genreSet = new HashSet<>();

        for (String genreString : genreListStr) {
            genreSet.add(genreDao.getByName(genreString.trim()));
        }
        return genreSet;
    }

    public Genre insertGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genre = genreDao.save(genre);
        return genre;
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Genre genre : genreDao.getAll()) {
            result.append(genre.toString()).append("\n");
        }
        return result.toString();
    }
}
