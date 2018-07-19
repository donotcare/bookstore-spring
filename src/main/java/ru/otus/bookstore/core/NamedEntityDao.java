package ru.otus.bookstore.core;

public interface NamedEntityDao<T extends NamedEntity> {
    T findByName(String name);
    void create(T entity);
}
