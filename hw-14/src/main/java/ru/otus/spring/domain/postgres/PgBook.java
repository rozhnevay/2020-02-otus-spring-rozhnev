package ru.otus.spring.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@ToString(exclude = {"commentList", "genreSet"})
public class PgBook {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToOne(targetEntity = PgAuthor.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private PgAuthor author;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(targetEntity = PgComment.class, fetch = FetchType.LAZY)
  @OrderBy("created_date DESC")
  @JoinColumn(name = "book_id")
  private List<PgComment> commentList;

  @Fetch(FetchMode.SUBSELECT)
  @ManyToMany(targetEntity = PgGenre.class, fetch = FetchType.EAGER)
  @JoinTable(name = "books_genres", joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<PgGenre> genreSet;

  public String toStringFull() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author=" + author +
        ", commentList=" + commentList +
        ", genreSet=" + genreSet +
        '}';
  }
}

