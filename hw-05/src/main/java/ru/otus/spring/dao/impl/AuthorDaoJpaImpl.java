package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.AuthorNotFoundException;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class AuthorDaoJpaImpl implements AuthorDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Author getById(long id) throws AuthorNotFoundException {
        Author author = em.find(Author.class, id);
        if (author == null) {
            throw new AuthorNotFoundException("Автор с id = " + id + " не найден");
        }
        return author;
    }


    @Override
    public Author getByIdWithBooks(long id) throws AuthorNotFoundException {
        EntityGraph<?> entityGraph = em.getEntityGraph("author-books");
        TypedQuery<Author> query = em.createQuery("select s from Author s join fetch s.books", Author.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        try {
            return query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new AuthorNotFoundException("Автор с id = " + id + " не найден");
        }
    }

    @Override
    public Author getByName(String name) throws AuthorNotFoundException {
        TypedQuery<Author> query = em.createQuery("select s " +
                        "from Author s " +
                        "where s.name = :name",
                Author.class);
        query.setParameter("name", name);
        try {
            return query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new AuthorNotFoundException("Автор с именем " + name + " не найден");
        }
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select s " +
                        "from Author s ",
                Author.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void remove(Author author) {
        author = em.merge(author);
        em.remove(author);
    }
}
