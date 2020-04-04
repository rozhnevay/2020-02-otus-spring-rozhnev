package ru.otus.spring.service;

public interface LibraryService {

    String list(String entity);

    String insertAuthor(String name);

    String insertGenre(String name);

    String insertBook(String name, String authorStr, String genresStr);

    String deleteBook(int id);

    String updateBook(int id, String name, String authorStr, String genresStr);
}
