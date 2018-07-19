package ru.otus.bookstore.book.genre;

import java.util.Map;
import java.util.Set;

public interface BookGenreDao {
    Map<Long, Set<BookGenre>> getAll();

    Set<BookGenre> getGenresByBookId(long bookId);

    void addGenre(BookGenre genre);

    void removeGenre(BookGenre genre);
}
