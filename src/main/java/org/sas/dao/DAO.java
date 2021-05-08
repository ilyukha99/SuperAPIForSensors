package org.sas.dao;

public interface DAO<E, K> {
    K create(E entity);
    E read(K key);
    void update(E entity);
    void delete(E entity);
}
