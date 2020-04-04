package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.impl.AuthorDaoJdbcImpl;
import ru.otus.spring.dao.impl.BookDaoJdbcImpl;
import ru.otus.spring.dao.impl.GenreDaoJdbcImpl;
import ru.otus.spring.domain.Book;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDaoJdbcImpl.class, AuthorDaoJdbcImpl.class, GenreDaoJdbcImpl.class})
public class BookDaoJdbcTest {
    public static final String TEST_BOOK = "TestBook";
    public static final String TEST_AUTHOR = "Some Author";
    public static final String TEST_GENRE = "Some Genre 1";
    @Autowired
    private BookDaoJdbcImpl bookDao;

    @Autowired
    private AuthorDaoJdbcImpl authorDao;

    @Autowired
    private GenreDaoJdbcImpl genreDao;

    @Test
    void shouldReturnExpectedBookCount() {
        assertThat(bookDao.getAll().size()).isEqualTo(1);
    }

    @Test
    void shouldInsertBook() {
        bookDao.insert(
                new Book(TEST_BOOK,
                        authorDao.getByName(TEST_AUTHOR),
                        new HashSet<>(Collections.singletonList(genreDao.getByName(TEST_GENRE)))));
        boolean found = false;
        for (Book book : bookDao.getAll()) {
            if (book.getName().equals("TestBook")) {
                found = true;
            }
        }
        assertThat(found).isTrue();
    }

}
