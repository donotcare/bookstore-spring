package ru.otus.bookstore.genre;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;

    public GenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Override
    public Genre create(String name) {
        Genre genre = Genre.create(name);
        dao.create(genre);
        return genre;
    }

    @Override
    public Genre findByName(String name) {
        try {
            return dao.findByName(name);
        } catch (IncorrectResultSizeDataAccessException e) {
            return create(name);
        }
    }
}
