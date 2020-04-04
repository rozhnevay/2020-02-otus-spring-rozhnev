package ru.otus.spring.service;

public interface ShellService {

    String list(String entity);

    String insertAuthor(String name);

    String insertGenre(String name);

    String insertBook(String name, String author, String genres);

    String deleteBook(int id);

    String updateBook(int id, String name, String author, String genres);

    String insertBookComment(int id, String name);
}
