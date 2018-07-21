package ru.otus.bookstore.book.genre;

import ru.otus.bookstore.genre.Genre;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Objects;

@Embeddable
public class BookGenre {
    @OneToOne
    private Genre genre;

    public BookGenre() {
    }

    private BookGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookGenre bookGenre = (BookGenre) o;
        return Objects.equals(genre, bookGenre.genre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(genre);
    }

    public static BookGenre create(Genre genre) {
        return new BookGenre(genre);
    }
}
