package ru.otus.bookstore.book;

import java.util.Collection;

public interface BookService {
    Book create(String name, String author, String genre);

    Collection<Book> findAll();
}
