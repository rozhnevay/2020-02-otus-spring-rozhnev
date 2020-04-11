package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.BookNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookDaoJpaImpl implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }
    @Override
    public Book getById(long id) throws BookNotFoundException {
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s join fetch s.author where s.id = :id",
                Book.class);
        query.setParameter("id", id);
        try {
            return query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new BookNotFoundException("Книга с id = " + id + " не найдена");
        }
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select s " +
                        "from Book s join fetch s.author",
                Book.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void remove(Book book) {
        book = em.merge(book);
        em.remove(book);
    }
}
