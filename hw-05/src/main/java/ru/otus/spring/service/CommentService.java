package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.BookNotFoundException;

import java.util.List;

public interface CommentService {

    void addComment(long bookId, Comment comment) throws BookNotFoundException;

    List<Comment> listComment(long bookId) throws BookNotFoundException;
}
