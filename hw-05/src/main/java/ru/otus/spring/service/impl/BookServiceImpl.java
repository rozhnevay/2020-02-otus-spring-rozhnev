package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreService genreService;

    private Book saveBook(Book book, String name, long authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException {
        book.setName(name);
        book.setAuthor(authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId)));
        book.setGenreSet(genreService.getGenresSetByString(genresStr));
        return bookRepository.save(book);
    }

    @Override
    public Book saveBook(String name, long authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException {
        return saveBook(new Book(), name, authorId, genresStr);
    }

    @Override
    public Book saveBook(long id, String name, long authorId, String genresStr) throws BookNotFoundException, GenreNotFoundException, AuthorNotFoundException {
        return saveBook(bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
                , name, authorId, genresStr);
    }

    @Override
    public void deleteBook(long id) throws BookNotFoundException {
        bookRepository.delete(
                bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Book book : bookRepository.findAll()) {
            result.append(book.toStringFull()).append("\n");
        }
        return result.toString();
    }
}
