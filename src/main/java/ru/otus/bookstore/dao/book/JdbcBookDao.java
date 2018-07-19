package ru.otus.bookstore.dao.book;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.book.Book;
import ru.otus.bookstore.book.BookDao;
import ru.otus.bookstore.book.author.BookAuthor;
import ru.otus.bookstore.book.author.BookAuthorDao;
import ru.otus.bookstore.book.genre.BookGenre;
import ru.otus.bookstore.book.genre.BookGenreDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JdbcBookDao implements BookDao {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final BookAuthorDao bookAuthorDao;
    private final BookGenreDao bookGenreDao;

    public JdbcBookDao(NamedParameterJdbcOperations jdbcOperations, BookAuthorDao bookAuthorDao, BookGenreDao bookGenreDao) {
        this.jdbcOperations = jdbcOperations;
        this.bookAuthorDao = bookAuthorDao;
        this.bookGenreDao = bookGenreDao;
    }

    @Override
    public Book findById(long id) {
        Set<BookAuthor> authors = bookAuthorDao.getAuthorsByBookId(id);
        Set<BookGenre> genres = bookGenreDao.getGenresByBookId(id);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcOperations.queryForObject("SELECT * FROM BOOK WHERE ID = :id", params,
                (rs, i) -> Book.of(rs.getLong("id"), rs.getString("name"), authors, genres));
    }

    @Override
    public Collection<Book> findAll() {
        Map<Long, Set<BookAuthor>> authorsByBookId = bookAuthorDao.getAll();
        Map<Long, Set<BookGenre>> genresByBookId = bookGenreDao.getAll();

        return jdbcOperations.query("SELECT * FROM BOOK",
                (rs, i) -> Book.of(rs.getLong("id"), rs.getString("name"), authorsByBookId.get(rs.getLong("id")), genresByBookId.get(rs.getLong("id"))));
    }

    @Override
    public void insert(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("ID is not null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        long id = jdbcOperations.update("INSERT INTO BOOK (NAME) VALUES(:name)", params);
        book.setId(id);
        for (BookAuthor author : book.getAuthors()) {
            bookAuthorDao.addAuthor(author);
        }
        for (BookGenre genre : book.getGenres()) {
            bookGenreDao.addGenre(genre);
        }
    }

    @Override
    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        jdbcOperations.update("UPDATE BOOK SET NAME = :name WHERE ID = :id", params);

        book.getAuthors().stream().filter(author -> author.getId() == null).forEach(bookAuthorDao::addAuthor);
        book.getGenres().stream().filter(genre -> genre.getId() == null).forEach(bookGenreDao::addGenre);
    }

    @Override
    public void delete(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        book.getAuthors().stream().filter(author -> author.getId() != null).forEach(bookAuthorDao::removeAuthor);
        book.getGenres().stream().filter(genre -> genre.getId() != null).forEach(bookGenreDao::removeGenre);

        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        jdbcOperations.update("DELETE FROM BOOK WHERE ID = :id", params);
    }


}
