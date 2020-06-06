INSERT INTO AUTHORS(NAME) VALUES ('Лев Толстой');
INSERT INTO AUTHORS(NAME) VALUES ('Александр Дюма');
INSERT INTO AUTHORS(NAME) VALUES ('Николай Карамзин');
INSERT INTO AUTHORS(NAME) VALUES ('Агата Кристи');
INSERT INTO AUTHORS(NAME) VALUES ('Федор Достоевский');
INSERT INTO AUTHORS(NAME) VALUES ('Уильям Шекспир');
INSERT INTO GENRES(NAME) VALUES ('Роман');
INSERT INTO GENRES(NAME) VALUES ('Повесть');
INSERT INTO GENRES(NAME) VALUES ('Детектив');
INSERT INTO GENRES(NAME) VALUES ('Трагедия');
INSERT INTO GENRES(NAME) VALUES ('Драма');
INSERT INTO GENRES(NAME) VALUES ('Приключения');
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Война и мир', 1);
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Граф Монте-Кристо', 2);
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Бедная Лиза', 3);
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Убийство в Восточном экспрессе', 4);
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Идиот', 5);
INSERT INTO BOOKS(NAME, AUTHOR_ID) VALUES ('Гамлет', 6);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (1, 1);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (1, 5);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (2, 1);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (3, 2);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (4, 3);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (5, 1);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (6, 4);
INSERT INTO BOOKS_GENRES(BOOK_ID, GENRE_ID) VALUES (2, 6);

INSERT INTO COMMENTS(COMMENT, AUTHOR, CREATED_DATE, BOOK_ID)
VALUES ('«Война и мир» состоит сплошь из персонажей, отвратительных с моральной точки зрения, никчемных и неприспособленных к жизни.', 'Vasya', now(), 1);
INSERT INTO COMMENTS(COMMENT, AUTHOR, CREATED_DATE, BOOK_ID)
VALUES ('Это то произведение, о котором можно говорить всю жизнь и так и не сказать главного! Насколько сильны эти рассуждения, справедливы высказывания героев и как ярко, насыщенно, полно показаны обе стороны медали жизни в те времена.', 'Elena Borodina', now(), 1);
INSERT INTO COMMENTS(COMMENT, AUTHOR, CREATED_DATE, BOOK_ID)
VALUES ('"Война и мир" по праву занимает верхушку 100 лучших книг мира. Я бы поставил её самой первой по большинству умозаключения тех людей, которые стремятся познать окружающий мир и в частности русскую душу. Ведь само название книги объявляет нам о содержании её, открывая границы всего мира и его жителей. А также причины войн и их катастрофические последствия для общества.', 'Пётр', now(), 1);
/* пароль qwe */
INSERT INTO USER_INFO(LOGIN, PASSWORD)
VALUES('user', '$2a$10$zNXmBn.oIgWEdAIx9Jy48uR0p.RD833PLWWnY7qLKuKzKrkljfW4q');
