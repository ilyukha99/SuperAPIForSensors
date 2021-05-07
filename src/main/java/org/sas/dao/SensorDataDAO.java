package org.sas.dao;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sas.model.SensorData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @NonNull
    public List<SensorData> getSensorDataByDate(@Nullable Timestamp startDate, @Nullable Timestamp endDate) {
        try (final  Session session = sessionFactory.openSession()){
            Query query = session.createQuery("from SensorData where record_date > " + "startDate" + "");
            return new LinkedList<>();
        }
    }
}
