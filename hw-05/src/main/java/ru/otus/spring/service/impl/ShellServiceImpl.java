package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.exceptions.GenreNotFoundException;
import ru.otus.spring.service.*;

import javax.transaction.Transactional;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellServiceImpl implements ShellService {

    private final CommentService commentService;
    private final GenreService genreService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Transactional
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
            case "genres":
                return genreService.list();
            default:
                return "Неверный формат команды";
        }
    }

    @Override
    @ShellMethod(key = "list_author_book", value = "Список книг автора. \n" +
            "           Пример:\n" +
            "               list_author_book 1")
    public String listAuthorBook(@ShellOption({"id"}) int id) throws AuthorNotFoundException {
        return authorService.listAuthorBooks(id).toString();
    }

    @Override
    @ShellMethod(key = "insert_author", value = "Добавить автора. \n" +
            "           Пример:\n" +
            "               insert_author Vasiliy")
    public String insertAuthor(@ShellOption({"name"}) String name) {
        return authorService.insertAuthor(name).toString();
    }

    @Override
    @ShellMethod(key = "insert_genre", value = "Добавить жанр. \n" +
            "           Пример:\n" +
            "               insert_genre Fantasy")
    public String insertGenre(@ShellOption({"name"}) String name) {
        return genreService.insertGenre(name).toString();
    }

    @Override
    @ShellMethod(key = "insert_book", value = "Добавить книгу. Параметры: название, Id автора, список жанров через запятую\n" +
            "           Пример:\n" +
            "               insert_book Book 1 'Some Genre 1,Some Genre 2'")
    public String insertBook(@ShellOption({"name"}) String name,
                             @ShellOption({"authorId"}) int authorId,
                             @ShellOption({"genres"}) String genres) throws AuthorNotFoundException, GenreNotFoundException {
        return bookService.saveBook(name, authorId, genres).toString();
    }

    @Override
    @ShellMethod(key = "delete_book", value = "Удалить книгу. Параметр: id\n" +
            "           Пример:\n" +
            "               delete_book 1")
    public String deleteBook(@ShellOption({"id"}) int id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return "Книга с id = " + id + " удалена";
    }

    @Override
    @ShellMethod(key = "update_book", value = "Обновить книгу. Параметры: id, название, id авторa, список жанров через запятую\n" +
            "           Пример:\n" +
            "               update_book 1 Book 1 'Some Genre 1,Some Genre 2'")
    public String updateBook(@ShellOption({"id"}) int id,
                             @ShellOption({"name"}) String name,
                             @ShellOption({"authorId"}) int authorId,
                             @ShellOption({"genres"}) String genres) throws GenreNotFoundException, BookNotFoundException, AuthorNotFoundException {
        return bookService.saveBook(id, name, authorId, genres).toString();
    }

    @Override
    @ShellMethod(key = "insert_book_comment", value = "Добавить комментарий на книгу. Параметры: id книги, комментарий\n" +
            "           Пример:\n" +
            "               insert_book_comment 1 'Не интересно'")
    public String insertBookComment(@ShellOption({"id"}) int id,
                                    @ShellOption({"comment"}) String comment) throws BookNotFoundException {
        commentService.addComment(id, comment);
        return "Комментарий добавлен";
    }
}
