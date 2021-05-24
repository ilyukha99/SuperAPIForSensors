package org.sas.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sas.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseDAO implements DAO<House, Integer> {
    private final SessionFactory sessionFactory;
    private UserDAO userDAO;

    @Autowired
    public HouseDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setUserDAO(@NonNull UserDAO userDAO) {
        this.userDAO = userDAO;
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

    public List<House> getHousesList(@NonNull int userId) {
        User user = userDAO.read(userId);
        try (final Session session = sessionFactory.openSession()) {
            Query<House> query = session.createQuery(
                    "from org.sas.model.House h where h.userId = :userId", House.class);
            query.setParameter("userId", user);
            return query.list();
        }
    }
}
