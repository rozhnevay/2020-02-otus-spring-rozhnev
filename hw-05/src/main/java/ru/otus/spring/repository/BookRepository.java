package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findAllByAuthorId(String id);

    Mono<Book> findById(String id);
}
