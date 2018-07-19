package ru.otus.bookstore.genre;

import ru.otus.bookstore.core.NamedEntity;

public class Genre implements NamedEntity {
    private Long id;
    private String name;

    private Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Genre of(long id, String name) {
        return new Genre(id, name);
    }

    public static Genre create(String name) {
        return new Genre(null, name);
    }
}
