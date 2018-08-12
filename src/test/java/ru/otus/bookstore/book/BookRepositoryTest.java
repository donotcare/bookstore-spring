package ru.otus.bookstore.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstore.author.Author;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
public class BookRepositoryTest {
    private final BookRepository bookDao;

    @Autowired
    public BookRepositoryTest(BookRepository bookDao) {
        this.bookDao = bookDao;
    }

    @Test
    @Sql("/stored-books.sql")
    public void insert() {
        Book book = Book.create("Effective java");
        assertThat(book.getId()).isNull();
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    @Sql("/stored-books.sql")
    public void findById() {
        Book book = bookDao.findById(2L).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualToIgnoringCase("Spring in action");
    }

    @Test
    @Sql("/stored-books.sql")
    public void update() {
        Book book = bookDao.findById(3L).orElse(null);
        book.setName("Hibernate in action ver. 2");
        bookDao.save(book);
        Book updatedBook = bookDao.findById(book.getId()).orElse(null);
        assertThat(updatedBook.getName()).isEqualToIgnoringCase("Hibernate in action ver. 2");
    }

    @Test
    @Sql("/stored-books.sql")
    public void delete() {
        Book book = bookDao.findById(3L).orElse(null);
        bookDao.delete(book);
        assertThat(bookDao.findById(3L).orElse(null)).isNull();
    }

    @Test
    @Sql("/stored-books.sql")
    public void addAuthor() {
        Book book = bookDao.findById(3L).orElse(null);
        assertThat(book.getAuthors().size()).isEqualTo(0);
        Author author = Author.of(3, "Christian Bauer");
        book.addAuthor(author);
        bookDao.save(book);
        Book bookWithAuthor = bookDao.findById(3L).orElse(null);
        assertThat(bookWithAuthor.getAuthors()).contains(author);
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
        Book book = bookDao.findFirstByName("Spring in action");
        assertThat(book.getName()).isEqualTo("Spring in action");
    }


}

