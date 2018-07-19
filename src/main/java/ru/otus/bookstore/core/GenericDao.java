package ru.otus.bookstore.core;

import java.util.Collection;

public interface GenericDao<T> {
    T findById(long id);

    Collection<T> findAll();

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}
