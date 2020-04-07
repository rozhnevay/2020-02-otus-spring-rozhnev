package ru.otus.spring.service;

import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;

public interface ShellService {

    String list(String entity);

    String insertAuthor(String name);

    String insertGenre(String name);

    String insertBook(String name, int authorId, String genres) throws AuthorNotFoundException, GenreNotFoundException;

    String deleteBook(int id) throws BookNotFoundException;

    String updateBook(int id, String name, int authorId, String genres) throws GenreNotFoundException, BookNotFoundException, AuthorNotFoundException;

    String insertBookComment(int id, String name) throws BookNotFoundException;

    String listAuthorBook(@ShellOption({"id"}) int id) throws AuthorNotFoundException;
}
