package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.exceptions.GenreNotFoundException;

import java.util.List;

@RequestMapping(path = "/v1/genre")
public interface GenreController {
    @GetMapping
    List<GenreDto> list();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody GenreDto genreDto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody GenreDto genreDto) throws GenreNotFoundException;
}
