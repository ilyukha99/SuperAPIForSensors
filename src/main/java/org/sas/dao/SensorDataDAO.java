package org.sas.dao;

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
    public void create(@NonNull SensorData sensorData){
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(sensorData);
            session.getTransaction().commit();
        }
    }

    @Override
    @Nullable
    public SensorData read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()){
            return session.get(SensorData.class, id);
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
    public List getSensorDataByDate(@Nullable Timestamp startDate, @Nullable Timestamp endDate) {
        try (final  Session session = sessionFactory.openSession()){
            if (startDate != null && endDate != null) {
//                Query query = session.createSQLQuery("select * from sensor_data where record_date > " + startDate + " and " + "");
                Query query = session.createQuery("from org.sas.model.SensorData sd where sd.recordDate between :startDate and :endDate");
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
                return query.list();
            }
            return new LinkedList<>();
        }
    }
}
