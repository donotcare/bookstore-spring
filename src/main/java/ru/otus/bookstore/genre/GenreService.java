package ru.otus.bookstore.genre;

public interface GenreService {
    Genre create(String name);

    Genre findByName(String name);
}
