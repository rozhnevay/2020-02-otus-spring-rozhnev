package ru.otus.spring.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@ToString(exclude = {"book"})
public class PgComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "comment", nullable = false)
  private String comment;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "created_date", nullable = false)
  private Instant createdDate;


  @ManyToOne(targetEntity = PgBook.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private PgBook book;

}

