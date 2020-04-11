package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

public interface CommentDao {
    Comment save(Comment comment);
}
