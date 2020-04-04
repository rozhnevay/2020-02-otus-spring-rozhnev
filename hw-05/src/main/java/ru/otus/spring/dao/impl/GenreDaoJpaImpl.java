package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class GenreDaoJpaImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre insert(Genre genre) {
        em.persist(genre);
        return genre;
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select s " +
                        "from Genre s " +
                        "where s.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getResultList().get(0);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select s " +
                        "from Genre s ",
                Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Genre s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
