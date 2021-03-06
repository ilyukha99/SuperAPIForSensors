package org.sas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.sas.model.SensorType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SensorTypeDAO implements DAO<SensorType, Integer> {
    private final SessionFactory sessionFactory;

    @Autowired
    public SensorTypeDAO(@NonNull SessionFactory factory) {
        sessionFactory = factory;
    }

    @Override
    public Integer create(@NonNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(sensorType);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public SensorType read(@NonNull Integer id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(SensorType.class, id);
        }
    }

    @Override
    public void update(@NonNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(sensorType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(sensorType);
            session.getTransaction().commit();
        }
    }
}
