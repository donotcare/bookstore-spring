package ru.otus.bookstore.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.book.author.BookAuthor;
import ru.otus.bookstore.book.author.BookAuthorDao;
import ru.otus.bookstore.book.genre.BookGenre;
import ru.otus.bookstore.book.genre.BookGenreDao;
import ru.otus.bookstore.dao.book.JdbcBookDao;
import ru.otus.bookstore.dao.book.author.JdbcBookAuthorDao;
import ru.otus.bookstore.dao.book.genre.JdbcBookGenreDao;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookDaoTest {
    private final BookDao bookDao;
    private final BookGenreDao genreDao;
    private final BookAuthorDao authorDao;

    @Autowired
    public BookDaoTest(BookDao bookDao, BookGenreDao genreDao, BookAuthorDao authorDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    @Test
    public void insert() {
        Book book = Book.create("Effective java");
        assertThat(book.getId()).isNull();
        bookDao.insert(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void findById() {
        Book book = bookDao.findById(2);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualToIgnoringCase("spring in action");
        assertThat(book.getAuthors().size()).isEqualTo(1);
        assertThat(book.getGenres().size()).isEqualTo(1);
    }

    @Test
    public void update() {
        Book book = bookDao.findById(3);
        book.setName("Hibernate in action ver. 2");
        bookDao.update(book);
        Book updatedBook = bookDao.findById(3);
        assertThat(updatedBook.getName()).isEqualToIgnoringCase("Hibernate in action ver. 2");
    }

    @Test
    public void delete() {
        Book book = bookDao.findById(4);
        bookDao.delete(book);
        assertThrows(DataRetrievalFailureException.class, () -> bookDao.findById(4));

        Set<BookGenre> genres = genreDao.getGenresByBookId(4);
        assertThat(genres.size()).isEqualTo(0);

        Set<BookAuthor> authors = authorDao.getAuthorsByBookId(4);
        assertThat(authors.size()).isEqualTo(0);
    }

    @Test
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
    public void findAll() {
        Collection<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Configuration
    public class AppConfig {
        @Bean
        public BookGenreDao bookGenreDao(NamedParameterJdbcOperations jdbcOperations) {
            return new JdbcBookGenreDao(jdbcOperations);
        }

        @Bean
        public BookAuthorDao bookAuthorDao(NamedParameterJdbcOperations jdbcOperations) {
            return new JdbcBookAuthorDao(jdbcOperations);
        }

        @Bean
        public BookDao bookDao(NamedParameterJdbcOperations jdbcOperations, BookAuthorDao bookAuthorDao, BookGenreDao bookGenreDao) {
            return new JdbcBookDao(jdbcOperations, bookAuthorDao, bookGenreDao);
        }
    }

}

