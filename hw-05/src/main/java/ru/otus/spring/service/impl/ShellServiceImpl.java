package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.ShellService;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellServiceImpl implements ShellService {

    private final BookService bookService;
    private final AuthorService authorService;

    @Override
    @ShellMethod(key = "list", value = "Список всех экземпляров сущности.\n" +
            "           Примеры:\n" +
            "               list authors\n" +
            "               list books\n" +
            "               list genres")
    public String list(@ShellOption({"entity"}) String entity) {
        switch (entity) {
            case "books":
                return bookService.list();
            case "authors":
                return authorService.list();
            default:
                return "Неверный формат команды";
        }
    }

    @Override
    @ShellMethod(key = "list_author_book", value = "Список книг автора. \n" +
            "           Пример:\n" +
            "               list_author_book 'Some Author'")
    public String listAuthorBook(@ShellOption({"name"}) String name) throws AuthorNotFoundException {
        return authorService.listAuthorBooks(name);
    }

    @Override
    @ShellMethod(key = "insert_book", value = "Добавить книгу. Параметры: название, Id автора, список жанров через запятую\n" +
            "           Пример:\n" +
            "               insert_book Book 'Some Author' 'Some Genre 1,Some Genre 2'")
    public String insertBook(@ShellOption({"name"}) String name,
                             @ShellOption({"author"}) String author,
                             @ShellOption({"genres"}) String genres) throws AuthorNotFoundException {
        return bookService.saveBook(name, author, genres).toString();
    }

    @Override
    @ShellMethod(key = "insert_author", value = "Добавить автора. \n" +
            "           Пример:\n" +
            "               insert_author Vasiliy")
    public String insertAuthor(@ShellOption({"name"}) String name) {
        return authorService.insertAuthor(name);
    }

    @Override
    @ShellMethod(key = "delete_book", value = "Удалить книгу. Параметр: id\n" +
            "           Пример:\n" +
            "               delete_book 1")
    public String deleteBook(@ShellOption({"id"}) String id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return "Книга с id = " + id + " удалена";
    }

    @Override
    @ShellMethod(key = "update_book", value = "Обновить книгу. Параметры: id, название, id авторa, список жанров через запятую\n" +
            "           Пример:\n" +
            "               update_book 1 Book 1 'Some Genre 1,Some Genre 2'")
    public String updateBook(@ShellOption({"id"}) String id,
                             @ShellOption({"name"}) String name,
                             @ShellOption({"authorId"}) String author,
                             @ShellOption({"genres"}) String genres) throws BookNotFoundException, AuthorNotFoundException {
        return bookService.saveBook(id, name, author, genres).toString();
    }

    @Override
    @ShellMethod(key = "insert_book_comment", value = "Добавить комментарий на книгу. Параметры: id книги, комментарий\n" +
            "           Пример:\n" +
            "               insert_book_comment 1 'Не интересно'")
    public String insertBookComment(@ShellOption({"id"}) String id,
                                    @ShellOption({"comment"}) String comment) throws BookNotFoundException {
        bookService.addComment(id, comment);
        return "Комментарий добавлен";
    }
}
