package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.exceptions.AuthorNotFoundException;

import java.util.List;

@RequestMapping(path = "/v1/author")
public interface AuthorController {
    @GetMapping
    List<AuthorDto> list();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody AuthorDto authorDto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) throws AuthorNotFoundException;
}
