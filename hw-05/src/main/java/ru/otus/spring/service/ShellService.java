package ru.otus.spring.service;

import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exceptions.BookNotFoundException;

public interface ShellService {

    String list(String entity);

    String insertBook(String name, String author, String genres);

    String deleteBook(String id) throws BookNotFoundException;

    String updateBook(String id, String name, String author, String genres) throws BookNotFoundException;

    String insertBookComment(String id, String name) throws BookNotFoundException;

    String listAuthorBook(@ShellOption({"id"}) String author);
}
