package ru.otus.bookstore.book.genre;

import ru.otus.bookstore.genre.Genre;

import java.util.Objects;

public class BookGenre {
    private Id id;
    private final long bookId;
    private final Genre genre;

    private BookGenre(Id id, long bookId, Genre genre) {
        this.id = id;
        this.bookId = bookId;
        this.genre = genre;
    }

    public long getBookId() {
        return bookId;
    }

    public Genre getGenre() {
        return genre;
    }

    public Id getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookGenre bookGenre = (BookGenre) o;
        return bookId == bookGenre.bookId &&
                Objects.equals(genre, bookGenre.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, genre);
    }

    @Override
    public String toString() {
        return genre.toString();
    }

    public static BookGenre create(long bookId, Genre genre) {
        return new BookGenre(null, bookId, genre);
    }

    public static BookGenre of(Id id, long bookId, Genre genre) {
        return new BookGenre(id, bookId, genre);
    }

    public static class Id {
        private final long bookId;

        private final long genreId;

        private Id(long bookId, long genreId) {
            this.bookId = bookId;
            this.genreId = genreId;
        }

        public static BookGenre.Id of(long bookId, long genreId) {
            return new BookGenre.Id(bookId, genreId);
        }

        public long getBookId() {
            return bookId;
        }

        public long getGenreId() {
            return genreId;
        }

    }
}
