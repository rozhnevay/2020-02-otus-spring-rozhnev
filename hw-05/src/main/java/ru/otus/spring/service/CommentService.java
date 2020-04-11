package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.BookNotFoundException;

public interface CommentService {
    Comment addComment(long bookId, String comment) throws BookNotFoundException;
}
