package ru.otus.spring.service;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;

public interface ShellService {

    String list(String entity);

    String insertBook(String name, String author, String genres) throws AuthorNotFoundException;

    @ShellMethod(key = "insert_author", value = "Добавить автора. \n" +
            "           Пример:\n" +
            "               insert_author Vasiliy")
    String insertAuthor(@ShellOption({"name"}) String name);

    String deleteBook(String id) throws BookNotFoundException;

    String updateBook(String id, String name, String author, String genres) throws BookNotFoundException, AuthorNotFoundException;

    String insertBookComment(String id, String name) throws BookNotFoundException;

    String listAuthorBook(@ShellOption({"id"}) String author) throws AuthorNotFoundException;
}
