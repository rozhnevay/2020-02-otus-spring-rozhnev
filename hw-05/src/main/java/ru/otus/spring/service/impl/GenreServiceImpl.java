package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.GenreService;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public Set<Genre> getGenresSetByString(String genresStr) throws GenreNotFoundException {
        String[] genreListStr = genresStr.split(",");
        Set<Genre> genreSet = new HashSet<>();

        for (String genreString : genreListStr) {
            String genre = genreString.trim();
            genreSet.add(genreRepository
                    .findByName(genre)
                    .orElseThrow(() -> new GenreNotFoundException(genre))
            );
        }
        return genreSet;
    }

    public Genre insertGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genre = genreRepository.save(genre);
        return genre;
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Genre genre : genreRepository.findAll()) {
            result.append(genre.toString()).append("\n");
        }
        return result.toString();
    }
}
