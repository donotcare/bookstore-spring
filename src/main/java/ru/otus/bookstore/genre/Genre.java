package ru.otus.bookstore.genre;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Genre {
    @Id
    private String id;
    private String name;

    private Genre() {
    }

    private Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Genre of(String id, String name) {
        return new Genre(id, name);
    }

    public static Genre create(String name) {
        return new Genre(null, name);
    }
}
