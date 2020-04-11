package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CommentDaoJpaImpl implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }


}
