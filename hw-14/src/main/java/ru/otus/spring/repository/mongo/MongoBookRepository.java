package ru.otus.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.mongo.MongoAuthor;
import ru.otus.spring.domain.mongo.MongoBook;

import java.util.List;

public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
  List<MongoBook> findByAuthor(MongoAuthor author);
}
