package ru.otus.bookstore.book.author;

import ru.otus.bookstore.author.Author;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Objects;

@Embeddable
public class BookAuthor {
    @OneToOne
    private Author author;

    public BookAuthor() {
    }

    private BookAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthor that = (BookAuthor) o;
        return Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author);
    }

    public static BookAuthor create(Author author) {
        return new BookAuthor(author);
    }

}
