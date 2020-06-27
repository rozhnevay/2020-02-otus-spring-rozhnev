package ru.otus.spring.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.postgres.PgComment;

public interface PgCommentRepository extends JpaRepository<PgComment, Long> {
}
