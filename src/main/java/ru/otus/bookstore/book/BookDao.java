package ru.otus.bookstore.book;

import java.util.Collection;

public interface BookDao {
    Book findById(long id);

    Collection<Book> findAll();

    void insert(Book entity);

    void update(Book entity);

    void delete(Book entity);

    Book findByName(String name);
}
