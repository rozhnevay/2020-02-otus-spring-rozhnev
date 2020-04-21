package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.service.AuthorService;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public String list() {
        return authorRepository.findAll().toString();
    }

    @Override
    public String insertAuthor(String author) {
        Author authorObj = authorRepository.findByName(author).orElse(null);
        if (authorObj == null) {
            authorObj = new Author();
            authorObj.setName(author);
            return authorRepository.save(authorObj).toString();
        }
        return "Автор с именем " + author + " уже существует";
    }

    @Override
    public String listAuthorBooks(String author) throws AuthorNotFoundException {
        List<Book> bookList = authorRepository.findByName(author).orElseThrow(() -> new AuthorNotFoundException(author)).getBookList();
        if (bookList != null) {
            return bookList.toString();
        }
        return Collections.emptyList().toString();
    }
}
