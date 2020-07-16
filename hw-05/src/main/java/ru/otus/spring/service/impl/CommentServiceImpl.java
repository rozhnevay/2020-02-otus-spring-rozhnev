package ru.otus.spring.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.service.CommentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @HystrixCommand(commandKey = "addCommentKey")
    public void addComment(long bookId, Comment comment) throws BookNotFoundException {
        comment.setBook(bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId))
        );
        comment.setCreatedDate(Instant.now());
        commentRepository.save(comment);
    }

    @Override
    @HystrixCommand(commandKey = "listCommentKey")
    public List<Comment> listComment(long bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        return book.getCommentList();
    }
}
