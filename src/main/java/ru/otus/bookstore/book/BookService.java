package ru.otus.bookstore.book;

import ru.otus.bookstore.book.comment.Comment;

import java.util.Collection;
import java.util.List;

public interface BookService {
    Book create(String name, String author, String genre);

    Collection<Book> findAll();

    void addComment(String book, String comment);

    Collection<Comment> getAllCommentsByBook(String book);
}
