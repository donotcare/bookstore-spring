package ru.otus.bookstore.book;

import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.book.author.BookAuthor;
import ru.otus.bookstore.book.genre.BookGenre;
import ru.otus.bookstore.genre.Genre;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book {
    private Long id;
    private String name;
    private final Set<BookAuthor> authors;
    private final Set<BookGenre> genres;

    private Book(Long id, String name, Set<BookAuthor> authors, Set<BookGenre> genres) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAuthor(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("Author id is null");
        }
        authors.add(BookAuthor.create(id, author));
    }

    public void addGenre(Genre genre) {
        if (genre.getId() == null) {
            throw new IllegalArgumentException("Genre id is null");
        }
        genres.add(BookGenre.create(id, genre));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<BookAuthor> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Set<BookGenre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }

    public static Book create(String name) {
        return new Book(null, name, new HashSet<>(), new HashSet<>());
    }

    public static Book of(long id, String name, Set<BookAuthor> authors, Set<BookGenre> genres) {
        return new Book(id, name, authors, genres);
    }


}
