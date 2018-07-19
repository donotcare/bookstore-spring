package ru.otus.bookstore.author;

import ru.otus.bookstore.core.NamedEntity;

import java.util.Objects;

public class Author implements NamedEntity {
    private Long id;
    private String name;

    private Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
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

    public static Author create(String name) {
        return new Author(null, name);
    }

    public static Author of(long id, String name) {
        return new Author(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
