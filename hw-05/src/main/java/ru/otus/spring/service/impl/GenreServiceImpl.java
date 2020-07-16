package ru.otus.spring.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.GenreService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;


    @HystrixCommand(commandKey = "saveGenreKey")
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @HystrixCommand(commandKey = "listGenreKey")
    public List<Genre> list() {
        return genreRepository.findAll();
    }
}
