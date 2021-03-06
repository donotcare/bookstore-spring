package ru.otus.bookstore.book;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.book.comment.Comment;
import ru.otus.bookstore.genre.Genre;

import java.util.*;

@Document
public class Book {
    @Id
    private String id;
    private String name;
    private Set<Author> authors;
    private Set<Genre> genres;
    private Collection<Comment> comments;

    private Book() {
    }

    private Book(String id, String name, Set<Author> authors, Set<Genre> genres, Collection<Comment> comments) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
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
        authors.add(author);
    }

    public void addGenre(Genre genre) {
        if (genre.getId() == null) {
            throw new IllegalArgumentException("Genre id is null");
        }
        genres.add(genre);
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

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Set<Genre> getGenres() {
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
