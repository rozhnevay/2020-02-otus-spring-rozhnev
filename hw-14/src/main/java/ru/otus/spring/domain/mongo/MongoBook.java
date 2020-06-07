package ru.otus.spring.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Book")
public class MongoBook {
  @Id
  private String id;

  private String name;

  @DBRef
  private MongoAuthor author;

  private List<String> commentList;

  private Set<String> genreSet;

}
