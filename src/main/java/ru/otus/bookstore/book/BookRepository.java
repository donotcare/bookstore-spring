package ru.otus.bookstore.book;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
    Book findFirstByName(String name);
}
