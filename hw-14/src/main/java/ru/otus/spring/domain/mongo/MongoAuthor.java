package ru.otus.spring.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Author")
@ToString(exclude = "bookList")
public class MongoAuthor {
  @Id
  private String id;

  private String name;

  @DBRef
  private List<MongoBook> bookList;
}
