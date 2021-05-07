package org.SuperApiForSensors.dao;

import com.sun.istack.NotNull;
import org.SuperApiForSensors.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO implements DAO<User, Integer> {
    private final SessionFactory sessionFactory;

    public UserDAO(@NotNull final SessionFactory factory) {
        sessionFactory = factory;
    }

    @Override
    public void create(@NotNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public User read(@NotNull Integer id) {
        try (final Session session = sessionFactory.openSession()) {
            final User result = session.get(User.class, id);
            return result != null ? result : new User();
        }
    }

    @Override
    public void update(@NotNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NotNull User user) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }
}
