package ru.otus.bookstore.dao.genre;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.core.AbstractNamedEntityDao;
import ru.otus.bookstore.genre.Genre;
import ru.otus.bookstore.genre.GenreDao;

public class JdbcGenreDao extends AbstractNamedEntityDao<Genre> implements GenreDao {
    public JdbcGenreDao(NamedParameterJdbcOperations jdbcOperations) {
        super(jdbcOperations, Genre.class);
    }
}
