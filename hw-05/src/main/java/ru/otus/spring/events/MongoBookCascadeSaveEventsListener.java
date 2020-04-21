package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {
    private final AuthorRepository authorRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Book> event) {
        super.onAfterSave(event);
        val book = event.getSource();
        Author author = book.getAuthor();
        if (author != null) {
            List<Book> bookList = author.getBookList() != null ? author.getBookList() : new ArrayList<>();
            bookList.add(book);
            author.setBookList(bookList);
            authorRepository.save(author);
        }
    }
}
