package ru.otus.bookstore.book.author;

import ru.otus.bookstore.author.Author;

import java.util.Objects;

public class BookAuthor {
    private Id id;
    private final long bookId;
    private final Author author;

    private BookAuthor(Id id, long bookId, Author author) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public Id getId() {
        return id;
    }

    public long getBookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthor that = (BookAuthor) o;
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, author);
    }

    @Override
    public String toString() {
        return author.toString();
    }

    public static BookAuthor create(long bookId, Author author) {
        return new BookAuthor(null, bookId, author);
    }

    public static BookAuthor of(Id id, long bookId, Author author) {
        return new BookAuthor(id, bookId, author);
    }

    public static class Id {
        private final long bookId;
        private final long authorId;

        private Id(long bookId, long authorId) {
            this.bookId = bookId;
            this.authorId = authorId;
        }

        public static Id of(long bookId, long authorId) {
            return new Id(bookId, authorId);
        }

        public long getBookId() {
            return bookId;
        }

        public long getAuthorId() {
            return authorId;
        }
    }
}
