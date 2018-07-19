package ru.otus.bookstore.book.author;

import java.util.Map;
import java.util.Set;

public interface BookAuthorDao {
    Map<Long, Set<BookAuthor>> getAll();

    Set<BookAuthor> getAuthorsByBookId(long bookId);

    void addAuthor(BookAuthor author);

    void removeAuthor(BookAuthor author);
}
