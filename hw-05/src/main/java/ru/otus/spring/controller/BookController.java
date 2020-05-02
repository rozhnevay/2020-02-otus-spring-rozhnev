package ru.otus.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

import java.util.List;

@RequestMapping(path = "/v1/book")
public interface BookController {

    @GetMapping
    Page<BookDto> list(@RequestParam int pageNum, @RequestParam int pageSize,
                       @RequestParam(required = false) List<String> sort);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody BookDto bookDto) throws AuthorNotFoundException, GenreNotFoundException;

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id) throws BookNotFoundException;

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody BookDto bookDto) throws AuthorNotFoundException, GenreNotFoundException;

    @GetMapping("{id}/comment")
    List<CommentDto> listComments(@PathVariable("id") long bookId) throws BookNotFoundException;

    @PostMapping("{id}/comment")
    void createComment(@PathVariable("id") long bookId, @RequestBody CommentDto commentDto) throws BookNotFoundException;
}
