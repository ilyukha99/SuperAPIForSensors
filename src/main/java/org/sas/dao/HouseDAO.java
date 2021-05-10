package org.sas.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sas.model.House;
import org.sas.model.SensorData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;

public class HouseDAO implements DAO<House, Integer> {

    private final SessionFactory sessionFactory;

    public HouseDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(@NonNull House house) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(house);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public House read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()) {
            final House result = session.get(House.class, id);
            if (result != null) {
                Hibernate.initialize(result.getUserId());
            }
            return result;
        }
    }

    @Override
    public void update(@NonNull House house) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(house);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull House house) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(house);
            session.getTransaction().commit();
        }
    }

    public List<House> getHousesList(@NonNull int user_id) {
        try (final Session session = sessionFactory.openSession()) {
            Query<House> query = session.createQuery(
                    "select h.id from org.sas.model.House h where h.userId = :userId", House.class);
            query.setParameter("userId", user_id);
            return query.list();
        }
    }


    public House getHouseById(@NonNull int user_id, @NonNull int house_id) {
        try (final Session session = sessionFactory.openSession()) {
            Query<House> query = session.createQuery(
                    "from org.sas.model.House h where h.userId = :userId and h.id = :houseId",
                    House.class);
            query.setParameter("userId", user_id);
            query.setParameter("houseId", house_id);
            return query.getSingleResult();
        }
    }
}
