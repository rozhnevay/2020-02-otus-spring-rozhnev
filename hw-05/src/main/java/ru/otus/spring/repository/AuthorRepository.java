package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(value = "authorBooks", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Author> findById(Long id);

    Optional<Author> findByName(String testAuthor);
}
