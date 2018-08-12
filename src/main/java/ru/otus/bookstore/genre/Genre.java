package ru.otus.bookstore.genre;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private Genre(){
    }

    private Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
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

    public static Genre of(long id, String name) {
        return new Genre(id, name);
    }

    public static Genre create(String name) {
        return new Genre(null, name);
    }
}
