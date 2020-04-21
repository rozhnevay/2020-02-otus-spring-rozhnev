package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.service.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private Book saveBook(Book book, String name, String author, String genresStr) throws AuthorNotFoundException {
        book.setName(name);
        book.setAuthor(authorRepository.findByName(author).orElseThrow(() -> new AuthorNotFoundException(author)));
        book.setGenreSet(new HashSet<>(Arrays.asList(genresStr.split(","))));
        return bookRepository.save(book);
    }

    @Override
    public Book saveBook(String name, String author, String genresStr) throws AuthorNotFoundException {
        return saveBook(new Book(), name, author, genresStr);
    }

    @Override
    public Book saveBook(String id, String name, String author, String genresStr) throws BookNotFoundException, AuthorNotFoundException {
        return saveBook(bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
                , name, author, genresStr);
    }

    @Override
    public void deleteBook(String id) throws BookNotFoundException {
        bookRepository.delete(
                bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    @Override
    public String list() {
        return bookRepository.findAll().toString();
    }

    @Override
    public void addComment(String id, String comment) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        List<String> comments = new ArrayList<>();
        if (book.getCommentList() != null) {
            comments = book.getCommentList();
        }
        comments.add(comment);
        book.setCommentList(comments);
        bookRepository.save(book);
    }
}
