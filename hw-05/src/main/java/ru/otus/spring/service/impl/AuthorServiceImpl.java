package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author insertAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        author = authorDao.save(author);
        return author;
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (Author author : authorDao.getAll()) {
            result.append(author.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public List<Book> listAuthorBooks(int authorId) throws AuthorNotFoundException {
        return authorDao.getById(authorId).getBooks();
    }
}
