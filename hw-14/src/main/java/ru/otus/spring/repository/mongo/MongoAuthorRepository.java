package ru.otus.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.mongo.MongoAuthor;

import java.util.Optional;

public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, String> {
  Optional<MongoAuthor> findByName(String name);
}
