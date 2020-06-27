package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.mongo.MongoBook;
import ru.otus.spring.domain.postgres.PgBook;
import ru.otus.spring.domain.postgres.PgComment;
import ru.otus.spring.domain.postgres.PgGenre;
import ru.otus.spring.repository.mongo.MongoAuthorRepository;

import java.util.stream.Collectors;


@Service
public class BookService {

  @Autowired
  MongoAuthorRepository mongoAuthorRepository;

  public MongoBook doMigrate(PgBook pgBook) {
    MongoBook mongoBook = new MongoBook();
    mongoBook.setAuthor(mongoAuthorRepository.findByName(pgBook.getAuthor().getName()).get());
    mongoBook.setCommentList(pgBook.getCommentList().stream().map(PgComment::getComment).collect(Collectors.toList()));
    mongoBook.setGenreSet(pgBook.getGenreSet().stream().map(PgGenre::getName).collect(Collectors.toSet()));
    mongoBook.setName(pgBook.getName());
    return mongoBook;
  }
}
