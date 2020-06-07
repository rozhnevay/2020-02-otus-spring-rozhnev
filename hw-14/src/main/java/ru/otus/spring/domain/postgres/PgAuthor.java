package ru.otus.spring.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
@ToString(exclude = {"books"})
@NamedEntityGraph(name = "authorBooks",
    attributeNodes = @NamedAttributeNode("books"))
public class PgAuthor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(targetEntity = PgBook.class)
  @JoinColumn(name = "author_id")
  private List<PgBook> books;
}
