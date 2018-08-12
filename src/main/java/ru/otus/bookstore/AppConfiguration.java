package ru.otus.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.bookstore.author.AuthorRepository;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.author.AuthorServiceImpl;
import ru.otus.bookstore.book.BookRepository;
import ru.otus.bookstore.book.BookService;
import ru.otus.bookstore.book.BookServiceImpl;
import ru.otus.bookstore.genre.GenreRepository;
import ru.otus.bookstore.genre.GenreService;
import ru.otus.bookstore.genre.GenreServiceImpl;

@Configuration
public class AppConfiguration {

    @Bean
    public GenreService genreService(GenreRepository dao) {
        return new GenreServiceImpl(dao);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService){
        return new BookServiceImpl(bookRepository, authorService, genreService);
    }

    @Bean
    public AuthorService authorService(AuthorRepository authorDao) {
        return new AuthorServiceImpl(authorDao);
    }

}
