package ru.otus.bookstore.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.dao.book.JdbcBookDao;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookDaoTest {
    private final BookDao bookDao;

    @Autowired
    public BookDaoTest(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Test
    @Sql("/stored-books.sql")
    public void insert() {
        Book book = Book.create("Effective java");
        assertThat(book.getId()).isNull();
        bookDao.insert(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    @Sql("/stored-books.sql")
    public void findById() {
        Book book = bookDao.findById(2);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualToIgnoringCase("Spring in action");
    }

    @Test
    @Sql("/stored-books.sql")
    public void update() {
        Book book = bookDao.findById(3);
        book.setName("Hibernate in action ver. 2");
        bookDao.update(book);
        Book updatedBook = bookDao.findById(book.getId());
        assertThat(updatedBook.getName()).isEqualToIgnoringCase("Hibernate in action ver. 2");
    }

    @Test
    @Sql("/stored-books.sql")
    public void delete() {
        Book book = bookDao.findById(3);
        bookDao.delete(book);
        assertThat(bookDao.findById(3)).isNull();
    }

    @Test
    @Sql("/stored-books.sql")
    public void addAuthor() {
        Book book = bookDao.findById(3);
        assertThat(book.getAuthors().size()).isEqualTo(0);
        Author author = Author.of(3, "Christian Bauer");
        book.addAuthor(author);
        bookDao.update(book);
        Book bookWithAuthor = bookDao.findById(3);
        assertThat(bookWithAuthor.getAuthors()).extracting("author").contains(author);
    }

    @Test
    @Sql("/stored-books.sql")
    public void findAll() {
        Collection<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @Sql("/stored-books.sql")
    public void findByName() {
        Book book = bookDao.findByName("Spring in action");
        assertThat(book.getName()).isEqualTo("Spring in action");
    }

    @Configuration
    public class AppConfig {

        @Bean
        public BookDao bookDao() {
            return new JdbcBookDao();
        }
    }

}

