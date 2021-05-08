package org.sas.dao;

import org.hibernate.Hibernate;
import org.sas.model.SensorData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.LinkedList;
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
    public List<SensorData> getSensorDataByDate(@Nullable Timestamp startDate, @Nullable Timestamp endDate) {
        try (final  Session session = sessionFactory.openSession()){
            if (startDate != null && endDate != null) {
                Query query = session.createQuery("from org.sas.model.SensorData sd where sd.recordTime between :startDate and :endDate");
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
                return query.list();
            } else if (startDate == null && endDate != null) {
                Query query = session.createQuery("from org.sas.model.SensorData sd where sd.recordTime <= :endDate");
                query.setParameter("endDate", endDate);
                return query.list();
            } else if (startDate != null) {
                Query query = session.createQuery("from org.sas.model.SensorData sd where sd.recordTime >= :startDate");
                query.setParameter("startDate", startDate);
                return query.list();
            } else {
                Query query = session.createQuery("from org.sas.model.SensorData limit");
                query.setMaxResults(100);
                return query.list();
            }
        }
    }
}
