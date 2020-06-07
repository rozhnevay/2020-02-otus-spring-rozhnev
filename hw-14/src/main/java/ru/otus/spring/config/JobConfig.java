package ru.otus.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.mongo.MongoAuthor;
import ru.otus.spring.domain.mongo.MongoBook;
import ru.otus.spring.domain.postgres.PgAuthor;
import ru.otus.spring.domain.postgres.PgBook;
import ru.otus.spring.repository.mongo.MongoAuthorRepository;
import ru.otus.spring.repository.mongo.MongoBookRepository;
import ru.otus.spring.repository.postgres.PgAuthorRepository;
import ru.otus.spring.repository.postgres.PgBookRepository;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;

import java.util.Collections;

@SuppressWarnings("all")
@Configuration
@Slf4j
public class JobConfig {
  private static final int CHUNK_SIZE = 5;
  private final Logger logger = LoggerFactory.getLogger("Batch");
  public static final String IMPORT_USER_JOB_NAME = "importUserJob";

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private PgAuthorRepository authorRepository;

  @Autowired
  private PgBookRepository bookRepository;

  @Autowired
  private MongoAuthorRepository mongoAuthorRepository;

  @Autowired
  private MongoBookRepository mongoBookRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private AuthorService authorService;

  @Autowired
  private BookService bookService;

  @StepScope
  @Bean
  @Qualifier("authorProcessor")
  public ItemProcessor<PgAuthor, MongoAuthor> authorProcessor(AuthorService authorService) {
    return (ItemProcessor<PgAuthor, MongoAuthor>) authorService::doMigrate;
  }

  @StepScope
  @Bean
  @Qualifier("authorBookProcessor")
  public ItemProcessor<MongoAuthor, MongoAuthor> authorBookProcessor(AuthorService authorService) {
    return (ItemProcessor<MongoAuthor, MongoAuthor>) authorService::doMigrateAuthorBooks;
  }

  @StepScope
  @Bean
  @Qualifier("bookProcessor")
  public ItemProcessor<PgBook, MongoBook> bookProcessor(BookService bookService) {
    return (ItemProcessor<PgBook, MongoBook>) bookService::doMigrate;
  }

  @StepScope
  @Bean
  public RepositoryItemReader<PgAuthor> authorPostgresItemReader() {
    RepositoryItemReader<PgAuthor> reader = new RepositoryItemReader<>();
    reader.setRepository(authorRepository);
    reader.setMethodName("findAll");
    reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    return reader;
  }

  @StepScope
  @Bean
  public RepositoryItemReader<MongoAuthor> authorMongoItemReader() {
    RepositoryItemReader<MongoAuthor> reader = new RepositoryItemReader<>();
    reader.setRepository(mongoAuthorRepository);
    reader.setMethodName("findAll");
    reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    return reader;
  }

  @StepScope
  @Bean
  public RepositoryItemReader<PgBook> bookPostgresItemReader() {
    RepositoryItemReader<PgBook> reader = new RepositoryItemReader<>();
    reader.setRepository(bookRepository);
    reader.setMethodName("findAll");
    reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    return reader;
  }

  @StepScope
  @Bean
  public RepositoryItemWriter<MongoAuthor> authorMongoItemWriter() {
    RepositoryItemWriter<MongoAuthor> writer = new RepositoryItemWriter<>();
    writer.setRepository(mongoAuthorRepository);
    writer.setMethodName("save");
    return writer;
  }

  @StepScope
  @Bean
  public RepositoryItemWriter<MongoBook> bookMongoItemWriter() {
    RepositoryItemWriter<MongoBook> writer = new RepositoryItemWriter<>();
    writer.setRepository(mongoBookRepository);
    writer.setMethodName("save");
    return writer;
  }

  @Bean
  public Job importUserJob(Step authorStep, Step bookStep, Step authorBookStep) {

    return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .start(authorStep)
        .next(bookStep)
        .next(authorBookStep)
        .listener(new JobExecutionListener() {
          @Override
          public void beforeJob(JobExecution jobExecution) {
            logger.info("Начало job");
          }

          @Override
          public void afterJob(JobExecution jobExecution) {
            logger.info("Конец job");
          }
        })
        .build();
  }

  @Bean
  public Step authorStep(RepositoryItemWriter<MongoAuthor> mongoAuthorWriter, RepositoryItemReader<PgAuthor> pgAuthorReader, ItemProcessor authorProcessor) {
    return stepBuilderFactory.get("authorStep")
        .chunk(CHUNK_SIZE)
        .reader(pgAuthorReader)
        .processor(authorProcessor)
        .writer(mongoAuthorWriter)
        .build();
  }

  @Bean
  public Step bookStep(RepositoryItemWriter<MongoBook> mongoBookWriter, RepositoryItemReader<PgBook> pgBookReader, ItemProcessor bookProcessor) {
    return stepBuilderFactory.get("bookStep")
        .chunk(CHUNK_SIZE)
        .reader(pgBookReader)
        .processor(bookProcessor)
        .writer(mongoBookWriter)
        .build();
  }

  @Bean
  public Step authorBookStep(RepositoryItemWriter<MongoAuthor> mongoAuthorWriter, RepositoryItemReader<MongoAuthor> mongoAuthorReader, ItemProcessor authorBookProcessor) {
    return stepBuilderFactory.get("authorBookStep")
        .chunk(CHUNK_SIZE)
        .reader(mongoAuthorReader)
        .processor(authorBookProcessor)
        .writer(mongoAuthorWriter)
        .build();
  }
}
