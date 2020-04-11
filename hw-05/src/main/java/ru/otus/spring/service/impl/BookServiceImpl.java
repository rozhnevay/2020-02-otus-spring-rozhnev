package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreService genreService;

    private Book saveBook(Book book, String name, int authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException {
        book.setName(name);
        book.setAuthor(authorDao.getById(authorId));
        book.setGenreSet(genreService.getGenresSetByString(genresStr));
        return bookDao.save(book);
    }

    @Override
    public Book saveBook(String name, int authorId, String genresStr) throws GenreNotFoundException, AuthorNotFoundException {
        return saveBook(new Book(), name, authorId, genresStr);
    }

    @Override
    public Book saveBook(int id, String name, int authorId, String genresStr) throws BookNotFoundException, GenreNotFoundException, AuthorNotFoundException {
        return saveBook(bookDao.getById(id), name, authorId, genresStr);
    }

    @Override
    public void deleteBook(int id) throws BookNotFoundException {
        bookDao.remove(bookDao.getById(id));
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Book book : bookDao.getAll()) {
            result.append(book.toStringFull()).append("\n");
        }
        return result.toString();
    }
}
