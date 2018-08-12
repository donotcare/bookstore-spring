package ru.otus.bookstore.genre;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository dao;

    public GenreServiceImpl(GenreRepository dao) {
        this.dao = dao;
    }

    @Override
    public Genre create(String name) {
        Genre genre = Genre.create(name);
        dao.save(genre);
        return genre;
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findByName(String name) {
        return dao.findFirstByName(name).orElse(create(name));
    }
}
