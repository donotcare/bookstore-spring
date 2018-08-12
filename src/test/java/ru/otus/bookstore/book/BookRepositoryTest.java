package ru.otus.bookstore.book;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstore.author.Author;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataMongoTest
public class BookRepositoryTest {
    private final MongoTemplate mongoTemplate;
    private final BookRepository bookDao;

    @Autowired
    public BookRepositoryTest(MongoTemplate mongoTemplate, BookRepository bookDao) {
        this.mongoTemplate = mongoTemplate;
        this.bookDao = bookDao;
    }

    @BeforeEach
    public void setUp() {
        Book springBook = Book.create("Spring in action");
        springBook.setId("2");
        mongoTemplate.insert(springBook);
        Book hibernateBook = Book.create("Hibernate in action");
        hibernateBook.setId("3");
        mongoTemplate.insert(hibernateBook);
    }

    @AfterEach
    public void clean() {
        mongoTemplate.dropCollection(Book.class);
    }

    @Test
    public void insert() {
        Book book = Book.create("Effective java");
        assertThat(book.getId()).isNull();
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void findById() {
        Book book = bookDao.findById("2").orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualToIgnoringCase("Spring in action");
    }

    @Test
    public void update() {
        Book book = bookDao.findById("3").orElse(null);
        book.setName("Hibernate in action ver. 2");
        bookDao.save(book);
        Book updatedBook = bookDao.findById(book.getId()).orElse(null);
        assertThat(updatedBook.getName()).isEqualToIgnoringCase("Hibernate in action ver. 2");
    }

    @Test
    public void delete() {
        Book book = bookDao.findById("3").orElse(null);
        bookDao.delete(book);
        assertThat(bookDao.findById("3").orElse(null)).isNull();
    }

    @Test
    public void addAuthor() {
        Book book = bookDao.findById("3").orElse(null);
        assertThat(book.getAuthors().size()).isEqualTo(0);
        Author author = Author.of("3", "Christian Bauer");
        book.addAuthor(author);
        bookDao.save(book);
        Book bookWithAuthor = bookDao.findById("3").orElse(null);
        assertThat(bookWithAuthor.getAuthors()).contains(author);
    }

    @Test
    public void addComment() {
        Book book = bookDao.findById("3").orElse(null);
        assertThat(book.getComments().size()).isEqualTo(0);
        book.addComment("Very good book");
        bookDao.save(book);
        Book bookWithComment = bookDao.findById("3").orElse(null);
        assertThat(bookWithComment.getComments()).extracting("text").contains("Very good book");
    }

    @Test
    public void findAll() {
        Collection<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    public void findByName() {
        Book book = bookDao.findFirstByName("Spring in action");
        assertThat(book.getName()).isEqualTo("Spring in action");
    }
}

