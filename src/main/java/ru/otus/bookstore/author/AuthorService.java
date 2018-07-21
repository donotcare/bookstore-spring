package ru.otus.bookstore.author;

public interface AuthorService {
    Author create(String name);

    Author findByName(String name);
}
