package ru.otus.bookstore.core;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNamedEntityDao<T extends NamedEntity> implements NamedEntityDao<T> {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final Class<T> clazz;

    protected AbstractNamedEntityDao(NamedParameterJdbcOperations jdbcOperations, Class<T> clazz) {
        this.jdbcOperations = jdbcOperations;
        this.clazz = clazz;
    }

    @Override
    public T findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return jdbcOperations.queryForObject(String.format("SELECT ID, NAME FROM %s WHERE NAME = :name", clazz.getSimpleName()), params, clazz);
    }

    @Override
    public void create(T entity) {
        if (entity.getId() != null) {
            throw new IllegalArgumentException("ID is not null");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("name", entity.getName());
        long id = jdbcOperations.update(String.format("INSERT INTO %s (NAME) VALUES(:name)", clazz.getSimpleName()), params);
        entity.setId(id);
    }
}
