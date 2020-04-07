package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.exceptions.BookNotFoundException;
import ru.otus.spring.service.CommentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookDao bookDao;
    private final CommentDao commentDao;

    @Override
    public Comment addComment(int bookId, String comment) throws BookNotFoundException {
        Comment commentObj = new Comment();
        commentObj.setBook(bookDao.getById(bookId));
        commentObj.setComment(comment);
        commentDao.save(commentObj);
        return commentObj;
    }
}
