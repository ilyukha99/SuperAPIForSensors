package org.sas.dao;

import org.springframework.lang.NonNull;
import org.sas.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.lang.Nullable;

public class UserDAO implements DAO<User, Integer> {
    private final SessionFactory sessionFactory;

    public UserDAO(@NonNull final SessionFactory factory) {
        sessionFactory = factory;
    }

    @Override
    public Integer create(@NonNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(user);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public User read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public void update(@NonNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }
}
