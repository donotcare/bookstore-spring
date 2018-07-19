package ru.otus.bookstore.dao.author;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.author.AuthorDao;
import ru.otus.bookstore.core.AbstractNamedEntityDao;

public class JdbcAuthorDao extends AbstractNamedEntityDao<Author> implements AuthorDao {

    public JdbcAuthorDao(NamedParameterJdbcOperations jdbcOperations) {
        super(jdbcOperations, Author.class);
    }
}
