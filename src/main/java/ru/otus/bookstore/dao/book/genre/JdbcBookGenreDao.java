package ru.otus.bookstore.dao.book.genre;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.book.genre.BookGenre;
import ru.otus.bookstore.book.genre.BookGenreDao;
import ru.otus.bookstore.genre.Genre;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JdbcBookGenreDao implements BookGenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    public JdbcBookGenreDao(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Map<Long, Set<BookGenre>> getAll() {
        Map<Long, Set<BookGenre>> genresByBookId = new HashMap<>();
        jdbcOperations.query("SELECT BOOK_ID, GENRE_ID, a.NAME " +
                "FROM BOOK_GENRE ba " +
                "INNER JOIN GENRE a ON ba.GENRE_ID = a.ID ", rs -> {
            long genreId = rs.getLong("GENRE_ID");
            long bookId = rs.getLong("BOOK_ID");
            if (genresByBookId.get(bookId) == null) {
                genresByBookId.put(bookId, new HashSet<>());
            }
            genresByBookId.get(bookId).add(BookGenre.of(BookGenre.Id.of(bookId, genreId), bookId,
                    Genre.of(genreId, rs.getString("NAME"))));
        });

        return genresByBookId;
    }

    @Override
    public Set<BookGenre> getGenresByBookId(long bookId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", bookId);
        return new HashSet<>(jdbcOperations.query("SELECT * FROM GENRE WHERE ID IN(SELECT GENRE_ID FROM BOOK_GENRE WHERE BOOK_ID = :id) ", params,
                (rs, i) -> BookGenre.of(BookGenre.Id.of(bookId, rs.getLong("id")), bookId, Genre.of(rs.getLong("id"), rs.getString("name")))));
    }

    @Override
    public void addGenre(BookGenre bookGenre) {
        if (bookGenre.getId() != null) {
            throw new IllegalArgumentException("Book author id is not null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookGenre.getBookId());
        params.put("genreId", bookGenre.getGenre().getId());

        jdbcOperations.update("INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES(:bookId, :genreId)", params);
    }

    @Override
    public void removeGenre(BookGenre genre) {
        if (genre.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", genre.getId().getBookId());
        params.put("genreId", genre.getId().getGenreId());
        jdbcOperations.update("DELETE FROM BOOK_GENRE WHERE GENRE_ID = :genreId AND BOOK_ID = :bookId", params);
    }
}
