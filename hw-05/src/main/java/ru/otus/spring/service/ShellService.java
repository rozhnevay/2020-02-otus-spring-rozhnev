package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellService {

    private final LibraryService libraryService;


    @ShellMethod(key = "list", value = "Список всех экземпляров сущности.\n" +
            "           Примеры:\n" +
            "               list authors\n" +
            "               list books\n" +
            "               list genres")
    public String list(@ShellOption({"entity"}) String entity) {
        return libraryService.list(entity);
    }

    @ShellMethod(key = "insert_author", value = "Добавить автора. \n" +
            "           Пример:\n" +
            "               insert_author Vasiliy")
    public String insertAuthor(@ShellOption({"name"}) String name) {
        return libraryService.insertAuthor(name);
    }

    @ShellMethod(key = "insert_genre", value = "Добавить жанр. \n" +
            "           Пример:\n" +
            "               insert_genre Fantasy")
    public String insertGenre(@ShellOption({"name"}) String name) {
        return libraryService.insertGenre(name);
    }

    @ShellMethod(key = "insert_book", value = "Добавить книгу. Параметры: название, автор, список жанров через запятую\n" +
            "           Пример:\n" +
            "               insert_book Book 'Some Author' 'Some Genre 1,Some Genre 2'")
    public String insertBook(@ShellOption({"name"}) String name,
                             @ShellOption({"author"}) String author,
                             @ShellOption({"genres"}) String genres) {
        return libraryService.insertBook(name, author, genres);
    }

    @ShellMethod(key = "delete_book", value = "Удалить книгу. Параметр: id\n" +
            "           Пример:\n" +
            "               delete_book 1")
    public String deleteBook(@ShellOption({"id"}) int id) {
        return libraryService.deleteBook(id);
    }

    @ShellMethod(key = "update_book", value = "Обновить книгу. Параметры: id, название, автор, список жанров через запятую\n" +
            "           Пример:\n" +
            "               update_book 1 Book 'Some Author' 'Some Genre 1,Some Genre 2'")
    public String insertBook(@ShellOption({"id"}) int id,
                             @ShellOption({"name"}) String name,
                             @ShellOption({"author"}) String author,
                             @ShellOption({"genres"}) String genres) {
        return libraryService.updateBook(id, name, author, genres);
    }
}
