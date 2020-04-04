CRUD по книгам на Spring Shell + JDBC.
Книга - Автор - Many-to-One
Книга - Жанр - Many-to-Many

Предустановлены:
    Автор: Some Author
    Жанры: Some Genre 1,  Some Genre 2

Основной CRUD:
insert_book: Добавить книгу. Параметры: название, автор, список жанров через запятую
           Пример:
               insert_book Book 'Some Author' 'Some Genre 1,Some Genre 2'
update_book: Обновить книгу. Параметры: id, название, автор, список жанров через запятую
           Пример:
               update_book 1 Book 'Some Author' 'Some Genre 1,Some Genre 2'
delete_book: Удалить книгу. Параметр: id
           Пример:
               delete_book 1
list: Список всех экземпляров сущности.
           Примеры:
               list authors
               list books
               list genres

Доп CRUD's:
insert_author: Добавить автора. 
           Пример:
               insert_author Vasiliy
insert_genre: Добавить жанр. 
           Пример:
               insert_genre Fantasy               

               
