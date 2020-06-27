package ru.otus.spring.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

@DataJpaTest
public class BookDaoJpaTest {
    private static final String TEST_BOOK = "TestBook";
    private static final String TEST_AUTHOR = "Test Author";
    private static final String TEST_GENRE = "Test Genre";
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldInsertBook() throws AuthorNotFoundException, GenreNotFoundException {
        Author author = new Author();
        author.setName(TEST_AUTHOR);
        authorRepository.save(author);

        Genre genre = new Genre();
        genre.setName(TEST_GENRE);
        genreRepository.save(genre);

        Book book = new Book();
        book.setName(TEST_BOOK);
        book.setAuthor(authorRepository.findByName(TEST_AUTHOR)
                .orElseThrow(() -> new AuthorNotFoundException(TEST_AUTHOR)));
        book.setGenreSet(new HashSet<>(Collections.singletonList(genreRepository
                .findByName(TEST_GENRE)
                .orElseThrow(() -> new GenreNotFoundException(TEST_GENRE)))));

        bookRepository.save(book);
        boolean found = false;
        for (Book bookItem : bookRepository.findAll()) {
            if (bookItem.getName().equals(TEST_BOOK)
                    && bookItem.getAuthor().getName().equals(TEST_AUTHOR)
                    && bookItem.getGenreSet().size() == 1
            ) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }

}
