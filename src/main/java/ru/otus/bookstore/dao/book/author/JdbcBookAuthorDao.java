package ru.otus.bookstore.dao.book.author;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.book.author.BookAuthor;
import ru.otus.bookstore.book.author.BookAuthorDao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JdbcBookAuthorDao implements BookAuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public JdbcBookAuthorDao(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Map<Long, Set<BookAuthor>> getAll() {
        Map<Long, Set<BookAuthor>> authorsByBookId = new HashMap<>();
        jdbcOperations.query("SELECT BOOK_ID, AUTHOR_ID, a.NAME " +
                "FROM BOOK_AUTHOR ba " +
                "INNER JOIN AUTHOR a ON ba.AUTHOR_ID = a.ID ", rs -> {
            long authorId = rs.getLong("AUTHOR_ID");
            long bookId = rs.getLong("BOOK_ID");
            if(authorsByBookId.get(bookId) == null) {
                authorsByBookId.put(bookId, new HashSet<>());
            }
            authorsByBookId.get(bookId).add(BookAuthor.of(BookAuthor.Id.of(bookId, authorId), bookId,
                    Author.of(authorId, rs.getString("NAME"))));
        });

        return authorsByBookId;
    }

    public Set<BookAuthor> getAuthorsByBookId(long bookId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", bookId);
        return new HashSet<>(jdbcOperations.query("SELECT * FROM AUTHOR WHERE ID IN(SELECT AUTHOR_ID FROM BOOK_AUTHOR WHERE BOOK_ID = :id) ", params,
                (rs, i) -> BookAuthor.of(BookAuthor.Id.of(bookId, rs.getLong("id")), bookId, Author.of(rs.getLong("id"), rs.getString("name")))));
    }

    @Override
    public void addAuthor(BookAuthor bookAuthor) {
        if (bookAuthor.getId() != null) {
            throw new IllegalArgumentException("Book author id is not null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookAuthor.getBookId());
        params.put("authorId", bookAuthor.getAuthor().getId());

        jdbcOperations.update("INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES(:bookId, :authorId)", params);
    }

    @Override
    public void removeAuthor(BookAuthor author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", author.getId().getBookId());
        params.put("authorId", author.getId().getAuthorId());
        jdbcOperations.update("DELETE FROM BOOK_AUTHOR WHERE AUTHOR_ID = :authorId AND BOOK_ID = :bookId", params);
    }
}
