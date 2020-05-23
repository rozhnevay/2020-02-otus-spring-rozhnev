package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.AuthorNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(AuthorRepository authorRepository, BookRepository bookRepository) {

        AuthorHandler authorHandler = new AuthorHandler(authorRepository);
        BookHandler bookHandler = new BookHandler(bookRepository, authorRepository);

        RouterFunction<ServerResponse> route = route()
                .GET("/author", accept(APPLICATION_JSON), authorHandler::list)
                .POST("/author", accept(APPLICATION_JSON), authorHandler::create)
                .GET("/author/{id}/book", accept(APPLICATION_JSON), bookHandler::listAuthorBook)
                .GET("/book", accept(APPLICATION_JSON), bookHandler::list)
                .POST("/book", accept(APPLICATION_JSON), bookHandler::create)
                .DELETE("/book/{id}", accept(APPLICATION_JSON), bookHandler::delete)
                .build();

        return route;
    }

    static class AuthorHandler {
        private final AuthorRepository repository;

        AuthorHandler(AuthorRepository repository) {
            this.repository = repository;
        }

        Mono<ServerResponse> list(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Author.class);
        }

        Mono<ServerResponse> create(ServerRequest request) {
            Mono<Author> authorMono = request.bodyToMono(Author.class);
            return ok().contentType(APPLICATION_JSON).body(
                    authorMono.flatMap(repository::save), Author.class);
        }
    }

    static class BookHandler {
        private final BookRepository bookRepository;
        private final AuthorRepository authorRepository;

        BookHandler(BookRepository bookRepository, AuthorRepository authorRepository) {
            this.bookRepository = bookRepository;
            this.authorRepository = authorRepository;
        }

        Mono<ServerResponse> list(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(bookRepository.findAll(), Book.class);
        }

        Mono<ServerResponse> listAuthorBook(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(bookRepository.findAllByAuthorId(request.pathVariable("id")), Book.class);
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            bookRepository.findById(request.pathVariable("id")).flatMap(bookRepository::delete);
            return ok().body(fromObject("deleted"));
        }

        Mono<ServerResponse> create(ServerRequest request) {
            Mono<Book> bookMono = request.bodyToMono(Book.class);
            return ok().contentType(APPLICATION_JSON).body(
                    bookMono.flatMap(book -> {
                        Mono<Author> fallback = Mono.error(new AuthorNotFoundException(book.getAuthor().getName()));
                        Mono<Author> author = authorRepository.findByName(book.getAuthor().getName()).switchIfEmpty(fallback);
                        return author.flatMap(item -> {
                            book.setAuthor(item);
                            return bookRepository.save(book);
                        });
                    }), Book.class);
        }
    }

}
