package org.sas.dao;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sas.model.SensorData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class SensorDataDAO  implements DAO<SensorData, Integer>{
    private final SessionFactory sessionFactory;

    public SensorDataDAO(@NotNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(@NotNull SensorData sensorData) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(sensorData);
            session.getTransaction().commit();
        }
    }

    @Override
    @Nullable
    public SensorData read(@NotNull Integer id) {
        try (final Session session = sessionFactory.openSession()){
            return session.get(SensorData.class, id);
        }
    }

    @Override
    public void update(@NotNull SensorData sensorData) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(sensorData);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(SensorData sensorData) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(sensorData);
            session.getTransaction().commit();
        }
    }
}
