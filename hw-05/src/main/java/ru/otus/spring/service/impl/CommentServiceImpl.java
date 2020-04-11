package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public Comment addComment(long bookId, String comment) throws BookNotFoundException {
        Comment commentObj = new Comment();
        commentObj.setBook(bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId))
        );
        commentObj.setComment(comment);
        commentRepository.save(commentObj);
        return commentObj;
    }
}
