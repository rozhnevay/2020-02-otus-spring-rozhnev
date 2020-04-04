package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class CommentDaoJpaImpl implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment insert(Comment comment) {
        em.persist(comment);
        return comment;
    }


}
