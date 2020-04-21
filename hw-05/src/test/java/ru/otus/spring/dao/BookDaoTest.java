package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class BookDaoTest {
    private static final String TEST_BOOK = "TestBook";
    private static final String TEST_AUTHOR = "Test Author";
    private static final String TEST_GENRE = "Test Genre";
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void shouldInsertBook() {


        Book book = new Book();
        book.setName(TEST_BOOK);

        Author author = new Author();
        author.setName(TEST_AUTHOR);
        book.setAuthor(authorRepository.save(author));
        book.setGenreSet(new HashSet<>(Collections.singletonList(TEST_GENRE)));

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
