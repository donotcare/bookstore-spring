package ru.otus.bookstore.book;

import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.book.author.BookAuthor;
import ru.otus.bookstore.book.comment.Comment;
import ru.otus.bookstore.book.genre.BookGenre;
import ru.otus.bookstore.genre.Genre;

import javax.persistence.*;
import java.util.*;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<BookAuthor> authors;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<BookGenre> genres;
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<Comment> comments;

    private Book() {
    }

    private Book(Long id, String name, Set<BookAuthor> authors, Set<BookGenre> genres, Collection<Comment> comments) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
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
        authors.add(BookAuthor.create(author));
    }

    public void addGenre(Genre genre) {
        if (genre.getId() == null) {
            throw new IllegalArgumentException("Genre id is null");
        }
        genres.add(BookGenre.create(genre));
    }

    public void addComment(String comment) {
        comments.add(Comment.create(comment));
    }

    public Collection<Comment> getComments() {
        return comments;
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
        return new Book(null, name, new HashSet<>(), new HashSet<>(), new ArrayList<>());
    }

}
