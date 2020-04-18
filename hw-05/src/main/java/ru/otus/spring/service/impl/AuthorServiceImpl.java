package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.service.AuthorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final BookRepository bookRepository;

    @Override
    public String list() {
        Set<String> authorSet = new HashSet<>();
        bookRepository.findAll().forEach(item -> authorSet.add(item.getAuthor()));
        return authorSet.toString();
    }

    @Override
    public List<Book> listAuthorBooks(String author) {
        Book matcherObject = new Book();
        matcherObject.setAuthor(author);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().
                withMatcher("author", exact());
        Example<Book> example = Example.of(matcherObject, matcher);
        return bookRepository.findAll(example);
    }
}
