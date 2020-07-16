package ru.otus.spring.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.BookService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @HystrixCommand(commandKey = "saveBookKey")
    public Book save(Book book) throws GenreNotFoundException, AuthorNotFoundException {
        long authorId = book.getAuthor().getId();
        Set<Genre> genreSet = book.getGenreSet();
        /* Проверяем, что автор существует */
        authorRepository.findById(authorId)
            .orElseThrow(() -> new AuthorNotFoundException(authorId));

        /* Проверяем, что жанры существуют */
        for (Genre genre : genreSet) {
            genreRepository
                .findById(genre.getId())
                .orElseThrow(() -> new GenreNotFoundException(genre.getName()));
        }

        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(commandKey = "deleteBookKey")
    public void deleteBook(long id) throws BookNotFoundException {
        bookRepository.delete(
            bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    @Override
    @HystrixCommand(commandKey = "listBookKey", fallbackMethod = "buildFallbackBooks")
    public Page<Book> list(Pageable pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> buildFallbackBooks(Pageable pageRequest) {
        return Page.empty();
    }
}
