package org.sas.dao;

import org.hibernate.Hibernate;
import org.sas.model.SensorData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;

public class SensorDataDAO  implements DAO<SensorData, Integer>{
    private final SessionFactory sessionFactory;

    public SensorDataDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(@NonNull SensorData sensorData){
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Integer id = (Integer) session.save(sensorData);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public SensorData read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()){
            SensorData sensorData = session.get(SensorData.class, id);
            if (sensorData != null) {
                Hibernate.initialize(sensorData.getSensor());
            }
            return sensorData;
        }
    }

    @Override
    public void update(@NonNull SensorData sensorData) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(sensorData);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull SensorData sensorData) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(sensorData);
            session.getTransaction().commit();
        }
    }

    @NonNull
    public List<SensorData> getSensorDataByDate(@Nullable Long startDate, @Nullable Long endDate) {
        try (final  Session session = sessionFactory.openSession()){
            if (startDate != null && endDate != null) {
                Query<SensorData> query = session.createQuery("from org.sas.model.SensorData sd where " +
                        "sd.time between :startDate and :endDate", SensorData.class);
                query.setParameter("startDate", new Timestamp(startDate));
                query.setParameter("endDate", new Timestamp(endDate));
                return query.list();
            } else if (startDate == null && endDate != null) {
                Query<SensorData> query = session.createQuery("from org.sas.model.SensorData sd where " +
                        "sd.time <= :endDate", SensorData.class);
                query.setParameter("endDate",  new Timestamp(endDate));
                return query.list();
            } else if (startDate != null) {
                Query<SensorData> query = session.createQuery("from org.sas.model.SensorData sd where " +
                        "sd.time >= :startDate", SensorData.class);
                query.setParameter("startDate", new Timestamp(startDate));
                return query.list();
            } else {
                Query<SensorData> query = session.createQuery("from org.sas.model.SensorData limit",
                        SensorData.class);
                return query.list();
            }
        }
    }
}
