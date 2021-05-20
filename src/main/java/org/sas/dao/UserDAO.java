package org.sas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.sas.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.hibernate.query.Query;

@Component
public class UserDAO implements DAO<User, Integer> {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(@NonNull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    public boolean tokenExists(@NonNull String userToken) {
        try(Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from org.sas.model.User user" +
                    " where user.token = :token", User.class);
            query.setParameter("token", userToken);
            return !query.list().isEmpty();
        }
    }

    public boolean loginExists(@NonNull String login) {
        try(Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from org.sas.model.User user" +
                    " where user.login = :login", User.class);
            query.setParameter("login", login);
            return !query.list().isEmpty();
        }
    }

    public boolean sensorTokenExists(@NonNull String sensorToken) {
        try(Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from org.sas.model.User user" +
                    " where user.sensorToken = :sensorToken", User.class);
            query.setParameter("sensorToken", sensorToken);
            return !query.list().isEmpty();
        }
    }

    public User findByLogin(String login) {
        try(Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from org.sas.model.User user" +
                    " where user.login = :login", User.class);
            query.setParameter("login", login);
            return query.list().get(0);
        }
    }
}
