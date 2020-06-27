package ru.otus.spring.repository.postgres;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.postgres.PgAuthor;

import java.util.Optional;

public interface PgAuthorRepository extends JpaRepository<PgAuthor, Long> {
  @EntityGraph(value = "authorBooks", type = EntityGraph.EntityGraphType.LOAD)
  Optional<PgAuthor> findById(Long id);

  Optional<PgAuthor> findByName(String testAuthor);
}
