package ru.otus.bookstore.author;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao dao;

    public AuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    public Author create(String name) {
        Author author = Author.create(name);
        dao.create(author);
        return author;
    }

    public Author findByName(String name){
        try{
            return dao.findByName(name);
        } catch (IncorrectResultSizeDataAccessException e) {
            return create(name);
        }
    }
}
