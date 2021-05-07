package org.sas.dao;

public interface DAO<E, K> {
    void create(E entity);
    E read(K key);
    void update(E entity);
    void delete(E entity);
}
