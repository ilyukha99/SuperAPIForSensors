package org.sas.dao;

import com.sun.istack.NotNull;
import org.sas.model.SensorType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SensorTypeDAO implements DAO<SensorType, Integer> {
    private SessionFactory sessionFactory;

    @Override
    public void create(@NotNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(sensorType);
            session.getTransaction().commit();
        }
    }

    @Override
    public SensorType read(@NotNull Integer id) {
        try(Session session = sessionFactory.openSession()) {
            SensorType result = session.get(SensorType.class, id);
            return result != null ? result : new SensorType();
        }
    }

    @Override
    public void update(@NotNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(sensorType);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NotNull SensorType sensorType) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(sensorType);
            session.getTransaction().commit();
        }
    }
}
