package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.GenreNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GenreDaoJpaImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) throws GenreNotFoundException {
        TypedQuery<Genre> query = em.createQuery("select s " +
                        "from Genre s " +
                        "where s.name = :name",
                Genre.class);
        query.setParameter("name", name);
        try {
            return query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new GenreNotFoundException("Жанр с именем " + name + " не найден");
        }
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select s " +
                        "from Genre s ",
                Genre.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void remove(Genre genre) {
        genre = em.merge(genre);
        em.remove(genre);
    }
}
