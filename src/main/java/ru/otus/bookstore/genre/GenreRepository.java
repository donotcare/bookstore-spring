package ru.otus.bookstore.genre;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findFirstByName(String name);
}
