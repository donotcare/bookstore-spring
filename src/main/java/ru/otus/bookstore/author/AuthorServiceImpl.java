package ru.otus.bookstore.author;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository dao;

    public AuthorServiceImpl(AuthorRepository dao) {
        this.dao = dao;
    }

    public Author create(String name) {
        Author author = Author.create(name);
        dao.save(author);
        return author;
    }

    @Transactional(readOnly = true)
    public Author findByName(String name) {
        return dao.findFirstByName(name).orElse(create(name));
    }
}
