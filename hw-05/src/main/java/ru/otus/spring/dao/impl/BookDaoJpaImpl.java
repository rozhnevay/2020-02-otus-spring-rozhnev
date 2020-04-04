package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class BookDaoJpaImpl implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(int id, Book book) {
        book.setId(id);
        return em.merge(book);
    }

    @Override
    public Book getById(long id) {
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s join fetch s.author where s.id = :id",
                Book.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s join fetch s.author left join fetch s.commentList",
                Book.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
