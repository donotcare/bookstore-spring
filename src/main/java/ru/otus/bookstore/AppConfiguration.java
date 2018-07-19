package ru.otus.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.author.AuthorDao;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.author.AuthorServiceImpl;
import ru.otus.bookstore.book.BookDao;
import ru.otus.bookstore.book.BookService;
import ru.otus.bookstore.book.BookServiceImpl;
import ru.otus.bookstore.book.author.BookAuthorDao;
import ru.otus.bookstore.book.genre.BookGenreDao;
import ru.otus.bookstore.dao.author.JdbcAuthorDao;
import ru.otus.bookstore.dao.book.JdbcBookDao;
import ru.otus.bookstore.dao.book.author.JdbcBookAuthorDao;
import ru.otus.bookstore.dao.book.genre.JdbcBookGenreDao;
import ru.otus.bookstore.dao.genre.JdbcGenreDao;
import ru.otus.bookstore.genre.GenreDao;
import ru.otus.bookstore.genre.GenreService;
import ru.otus.bookstore.genre.GenreServiceImpl;

@Configuration
public class AppConfiguration {

    @Bean
    public GenreDao genreDao(NamedParameterJdbcOperations jdbcOperations){
        return new JdbcGenreDao(jdbcOperations);
    }

    @Bean
    public GenreService genreService(GenreDao dao) {
        return new GenreServiceImpl(dao);
    }

    @Bean
    public BookService bookService(BookDao dao, AuthorService authorService, GenreService genreService){
        return new BookServiceImpl(dao, authorService, genreService);
    }

    @Bean
    public AuthorService authorService(AuthorDao authorDao) {
        return new AuthorServiceImpl(authorDao);
    }

    @Bean
    public AuthorDao authorDao(NamedParameterJdbcOperations jdbcOperations) {
        return new JdbcAuthorDao(jdbcOperations);
    }

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
