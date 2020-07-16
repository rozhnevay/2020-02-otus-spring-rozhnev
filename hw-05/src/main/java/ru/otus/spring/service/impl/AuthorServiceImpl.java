package ru.otus.spring.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.service.AuthorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @HystrixCommand(commandKey = "saveAuthorKey")
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    @HystrixCommand(commandKey = "listAuthorKey")
    public List<Author> list() {
        return authorRepository.findAll();
    }

    @Override
    @HystrixCommand(commandKey = "listAuthorBooksKey")
    public List<Book> listAuthorBooks(long authorId) throws AuthorNotFoundException {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId))
                .getBooks();
    }
}
