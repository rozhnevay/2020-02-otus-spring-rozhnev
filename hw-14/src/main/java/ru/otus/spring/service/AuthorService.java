package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.mongo.MongoAuthor;
import ru.otus.spring.domain.postgres.PgAuthor;
import ru.otus.spring.repository.mongo.MongoAuthorRepository;
import ru.otus.spring.repository.mongo.MongoBookRepository;

import java.util.stream.Collectors;

@Service
public class AuthorService {

  @Autowired
  MongoAuthorRepository mongoAuthorRepository;

  @Autowired
  MongoBookRepository mongoBookRepository;

  public MongoAuthor doMigrate(PgAuthor pgAuthor) {
    MongoAuthor mongoAuthor = new MongoAuthor();
    mongoAuthor.setName(pgAuthor.getName());
    return mongoAuthor;
  }

  public MongoAuthor doMigrateAuthorBooks(MongoAuthor mongoAuthor) {
    mongoAuthor.setBookList(mongoBookRepository.findByAuthor(mongoAuthor).stream().collect(Collectors.toList()));
    return mongoAuthor;
  }
}
