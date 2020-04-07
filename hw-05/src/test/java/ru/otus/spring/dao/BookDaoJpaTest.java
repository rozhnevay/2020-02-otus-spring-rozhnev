package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.impl.AuthorDaoJpaImpl;
import ru.otus.spring.dao.impl.BookDaoJpaImpl;
import ru.otus.spring.dao.impl.GenreDaoJpaImpl;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookDaoJpaImpl.class, AuthorDaoJpaImpl.class, GenreDaoJpaImpl.class})
public class BookDaoJpaTest {
    private static final String TEST_BOOK = "TestBook";
    private static final String TEST_AUTHOR = "Test Author";
    private static final String TEST_GENRE = "Test Genre";
    @Autowired
    private BookDaoJpaImpl bookDao;

    @Autowired
    private AuthorDaoJpaImpl authorDao;

    @Autowired
    private GenreDaoJpaImpl genreDao;

    @Test
    void shouldInsertBook() throws AuthorNotFoundException, GenreNotFoundException {
        Author author = new Author();
        author.setName(TEST_AUTHOR);
        authorDao.save(author);

        Genre genre = new Genre();
        genre.setName(TEST_GENRE);
        genreDao.save(genre);

        Book book = new Book();
        book.setName(TEST_BOOK);
        book.setAuthor(authorDao.getByName(TEST_AUTHOR));
        book.setGenreSet(new HashSet<>(Collections.singletonList(genreDao.getByName(TEST_GENRE))));

        bookDao.save(book);
        boolean found = false;
        for (Book bookItem : bookDao.getAll()) {
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
