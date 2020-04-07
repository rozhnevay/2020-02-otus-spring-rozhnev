DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));
CREATE UNIQUE INDEX GENRE_NAME ON GENRES (NAME);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), AUTHOR_ID BIGINT);
CREATE UNIQUE INDEX BOOK_NAME ON BOOKS (NAME, AUTHOR_ID);

ALTER TABLE BOOKS ADD CONSTRAINT BOOKS_AUTHOR_FK FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHORS;

DROP TABLE IF EXISTS BOOKS_GENRES;
CREATE TABLE BOOKS_GENRES(BOOK_ID BIGINT, GENRE_ID BIGINT);
CREATE UNIQUE INDEX BOOKS_GENRES_UNIQ ON BOOKS_GENRES (BOOK_ID, GENRE_ID);
ALTER TABLE BOOKS_GENRES ADD CONSTRAINT BOOKS_GENRES_GENRE_FK FOREIGN KEY(GENRE_ID) REFERENCES GENRES;
ALTER TABLE BOOKS_GENRES ADD CONSTRAINT BOOKS_GENRES_BOOK_FK FOREIGN KEY(BOOK_ID) REFERENCES BOOKS;

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS(ID BIGINT PRIMARY KEY AUTO_INCREMENT, COMMENT VARCHAR(1000) NOT NULL, BOOK_ID BIGINT);
ALTER TABLE COMMENTS ADD CONSTRAINT COMMENTS_BOOK_FK FOREIGN KEY(BOOK_ID) REFERENCES BOOKS;
