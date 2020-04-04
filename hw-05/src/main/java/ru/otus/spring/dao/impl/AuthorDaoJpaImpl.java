package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class AuthorDaoJpaImpl implements AuthorDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author insert(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select s " +
                        "from Author s " +
                        "where s.name = :name",
                Author.class);
        query.setParameter("name", name);
        return query.getResultList().get(0);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select s " +
                        "from Author s ",
                Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Author s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
