package ru.otus.bookstore;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.bookstore.author.AuthorRepository;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.author.AuthorServiceImpl;
import ru.otus.bookstore.book.BookRepository;
import ru.otus.bookstore.book.BookService;
import ru.otus.bookstore.book.BookServiceImpl;
import ru.otus.bookstore.genre.GenreRepository;
import ru.otus.bookstore.genre.GenreService;
import ru.otus.bookstore.genre.GenreServiceImpl;

import java.io.IOException;

@Configuration
public class AppConfiguration {
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

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

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        return new MongoTemplate(mongoClient, MONGO_DB_NAME);
    }

}
