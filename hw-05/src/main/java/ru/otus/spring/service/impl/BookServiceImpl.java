package ru.otus.spring.service.impl;

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

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

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
    public void deleteBook(long id) throws BookNotFoundException {
        bookRepository.delete(
                bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    @Override
    public Page<Book> list(Pageable pageRequest) {
        return bookRepository.findAll(pageRequest);
    }
}
