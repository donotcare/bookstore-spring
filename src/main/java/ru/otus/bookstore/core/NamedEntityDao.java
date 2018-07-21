package ru.otus.bookstore.core;

import org.springframework.stereotype.Repository;

@Repository
public interface NamedEntityDao<T extends NamedEntity> {
    T findByName(String name);
    void create(T entity);
}
