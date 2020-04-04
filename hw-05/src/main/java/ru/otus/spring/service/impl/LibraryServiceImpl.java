package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.LibraryService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;

    @Override
    public String list(String entity) {
        StringBuilder result = new StringBuilder();
        switch (entity) {
            case "books":
                for (Book book : bookDao.getAll()) {
                    result.append(book.toString()).append("\n");
                }
                break;
            case "authors":
                for (Author author : authorDao.getAll()) {
                    result.append(author.toString()).append("\n");
                }
                break;
            case "genres":
                for (Genre genre : genreDao.getAll()) {
                    result.append(genre.toString()).append("\n");
                }
                break;
            default:
                return "Неверный формат команды";
        }

        return result.toString();
    }

    @Override
    public String insertAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        author = authorDao.insert(author);
        return author.toString();
    }

    @Override
    public String insertGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genre = genreDao.insert(genre);
        return genre.toString();
    }

    private Book createBookByParams(String name, String authorStr, String genresStr) {
        Author author;
        try {
            author = authorDao.getByName(authorStr);
        } catch (RuntimeException e) {
            throw new RuntimeException("Автор " + authorStr + " не найден");
        }

        List<String> genreListStr = Arrays.asList(genresStr.split(","));
        Set<Genre> genreSet = new HashSet<>();
        genreListStr.forEach(genre -> {
            try {
                genreSet.add(genreDao.getByName(genre.trim()));
            } catch (RuntimeException e) {
                throw new RuntimeException("Жанр " + genre + " не найден");
            }
        });
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenreSet(genreSet);
        return book;
    }

    @Override
    public String insertBook(String name, String authorStr, String genresStr) {
        Book book = bookDao.insert(createBookByParams(name, authorStr, genresStr));
        return book.toString();
    }

    @Override
    public String deleteBook(int id) {
        bookDao.deleteById(id);
        return "Книга с id = " + id + " удалена";
    }

    @Override
    public String updateBook(int id, String name, String authorStr, String genresStr) {
        try {
            bookDao.getById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Книга с id=" + id + " не найдена");
        }
        return bookDao.update(id, createBookByParams(name, authorStr, genresStr)).toString();
    }

    @Override
    public String addComment(int id, String commentStr) {
        try {
            Book book = bookDao.getById(id);
            Comment comment = new Comment();
            comment.setBook(book);
            comment.setComment(commentStr);
            commentDao.insert(comment);
            return bookDao.getById(id).toString();
        } catch (RuntimeException e) {
            throw new RuntimeException("Книга с id=" + id + " не найдена", e);
        }
    }
}
