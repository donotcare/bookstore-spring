package ru.otus.bookstore.dao.book;

import org.springframework.stereotype.Repository;
import ru.otus.bookstore.book.Book;
import ru.otus.bookstore.book.BookDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collection;


public class JdbcBookDao implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    @Transactional
    public Collection<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("ID is not null");
        }
        em.persist(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        em.merge(book);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("ID is null");
        }
        book = em.merge(book);
        em.remove(book);
    }

    @Override
    @Transactional
    public Book findByName(String name) {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
