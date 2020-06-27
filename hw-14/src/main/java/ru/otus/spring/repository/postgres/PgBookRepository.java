package ru.otus.spring.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.spring.domain.postgres.PgBook;

public interface PgBookRepository extends JpaRepository<PgBook, Long>, JpaSpecificationExecutor<PgBook> {
}
