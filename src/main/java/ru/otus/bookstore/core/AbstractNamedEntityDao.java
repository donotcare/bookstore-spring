package ru.otus.bookstore.core;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNamedEntityDao<T extends NamedEntity> implements NamedEntityDao<T> {
    @PersistenceContext
    private EntityManager em;

    private final NamedParameterJdbcOperations jdbcOperations;
    private final Class<T> clazz;

    protected AbstractNamedEntityDao(NamedParameterJdbcOperations jdbcOperations, Class<T> clazz) {
        this.jdbcOperations = jdbcOperations;
        this.clazz = clazz;
    }

    @Override
    @Transactional
    public T findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        TypedQuery<T> query = em.createQuery(String.format("SELECT e FROM %s e WHERE NAME = :name", clazz.getSimpleName()), clazz);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void create(T entity) {
        if (entity.getId() != null) {
            throw new IllegalArgumentException("ID is not null");
        }
        em.persist(entity);
    }
}
