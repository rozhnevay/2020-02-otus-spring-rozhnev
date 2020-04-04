package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJdbcImpl implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final JdbcOperations jdbc;

    @Override
    public Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        params.put("author_id", book.getAuthor().getId());
        namedParameterJdbcOperations.update("insert into books (`name`, author_id) values (:name, :author_id)", new MapSqlParameterSource(params), keyHolder);
        int id = keyHolder.getKey().intValue();
        insertBookGenres(id, book);
        return getById(id);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book = namedParameterJdbcOperations.queryForObject("select id, name " +
                        "from books b " +
                        "where id = :id",
                params,
                new BookMapper());
        Author author = namedParameterJdbcOperations.queryForObject(
                "select a.id, a.name " +
                        "from authors a join books b on b.author_id = a.id " +
                        "where b.id = :id",
                params,
                new AuthorDaoJdbcImpl.AuthorMapper());
        book.setAuthor(author);
        List<Genre> genreList = namedParameterJdbcOperations.query("select g.id, g.name from genres g " +
                        "join books_genres bg on g.id = bg.genre_id and bg.book_id = :id",
                params,
                new GenreDaoJdbcImpl.GenreMapper());
        book.setGenreSet(new HashSet<>(genreList));
        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = jdbc.query("select id, name from books", new BookMapper());
        List<Book> result = new ArrayList<>();
        bookList.forEach(item -> result.add(getById(item.getId())));
        return result;
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books_genres where book_id = :id", params);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Book(id, name);
        }
    }

    private void insertBookGenres(int id, Book book) {
        for (Genre genre : book.getGenreSet()) {
            Map<String, Object> genre_params = new HashMap<>();
            genre_params.put("book_id", id);
            genre_params.put("genre_id", genre.getId());
            namedParameterJdbcOperations.update("insert into books_genres (genre_id, book_id) values (:genre_id, :book_id)", genre_params);
        }
    }

    @Override
    public Book update(int id, Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", book.getName());
        params.put("author_id", book.getAuthor().getId());
        namedParameterJdbcOperations.update("update books set name = :name, author_id = :author_id where id = :id", params);
        namedParameterJdbcOperations.update("delete from books_genres where book_id = :id", params);
        insertBookGenres(id, book);
        return getById(id);
    }
}
