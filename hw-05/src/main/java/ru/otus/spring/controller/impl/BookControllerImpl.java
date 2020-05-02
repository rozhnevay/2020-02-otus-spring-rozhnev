package ru.otus.spring.controller.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.BookController;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    private final CommentService commentService;

    private final ModelMapper mapper;

    @Override
    public Page<BookDto> list(int pageNum, int pageSize, List<String> sort) {
        List<Sort.Order> sortOrderList = new ArrayList<>();
        if (sort != null) {
            sortOrderList = sort.stream()
                    .map((s) -> Sort.Order.by(s.split(":")[0])
                            .with(getDirection(s.split(":")[1]))
                            .ignoreCase()
                            .nullsFirst())
                    .collect(toList());
        }

        Pageable pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(sortOrderList));
        Page<Book> bookPage = bookService.list(pageRequest);

        return bookPage.map(this::convertEntityToDto);
    }

    @Override
    public void create(BookDto bookDto) throws AuthorNotFoundException, GenreNotFoundException {
        bookService.save(convertDtoToEntity(bookDto));
    }

    @Override
    public void delete(long id) throws BookNotFoundException {
        bookService.deleteBook(id);
    }

    @Override
    public void update(long id, BookDto bookDto) throws AuthorNotFoundException, GenreNotFoundException {
        Book book = convertDtoToEntity(bookDto);
        book.setId(id);
        bookService.save(book);
    }

    @Override
    public List<CommentDto> listComments(long bookId) throws BookNotFoundException {
        return commentService.listComment(bookId).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void createComment(long bookId, CommentDto commentDto) throws BookNotFoundException {
        commentService.addComment(bookId, convertDtoToEntity(commentDto));
    }

    private Book convertDtoToEntity(BookDto bookDto) {
        return mapper.map(bookDto, Book.class);
    }

    private BookDto convertEntityToDto(Book book) {
        return mapper.map(book, BookDto.class);
    }

    private Comment convertDtoToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    private CommentDto convertEntityToDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    private Sort.Direction getDirection(String s) {
        return s == null || s.equals("ascend") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
