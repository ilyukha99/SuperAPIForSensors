package org.sas.dao;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.sas.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sas.model.Sensor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SensorDAO implements DAO<Sensor, Integer> {
    private final SessionFactory sessionFactory;
    private RoomDAO roomDAO;

    @Autowired
    public SensorDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setRoomDAO(@NonNull RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public Integer create(@NonNull Sensor sensor) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(sensor);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public Sensor read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()) {
            final Sensor result = session.get(Sensor.class, id);
            if (result != null) {
                Hibernate.initialize(result.getUser());
                Hibernate.initialize(result.getType());
            }
            return result;
        }
    }

    @Override
    public void update(@NonNull Sensor sensor) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(sensor);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull Sensor sensor) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(sensor);
            session.getTransaction().commit();
        }
    }

    public List<Sensor> getSensorsList(Integer roomId) {
        Room room = roomDAO.read(roomId);
        try (final Session session = sessionFactory.openSession()) {
            Query<Sensor> query = session.createQuery(
                    "from org.sas.model.Sensor s where s.roomId = :roomId", Sensor.class);
            query.setParameter("roomId", room);
            return query.list();
        }
    }

    public String getSensorOwnerLogin(int sensorId) {
        try (final Session session = sessionFactory.openSession()) {
            Query<Sensor> query = session.createQuery(
                    "from org.sas.model.Sensor s where s.id = :sensorId", Sensor.class);
            query.setParameter("sensorId", sensorId);
            return query.list().get(0).getUser().getLogin();
        }
    }
}
