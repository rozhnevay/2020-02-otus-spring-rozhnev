package ru.otus.spring.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.postgres.PgGenre;

import java.util.Optional;

public interface PgGenreRepository extends JpaRepository<PgGenre, Long> {
  Optional<PgGenre> findByName(String trim);
}
