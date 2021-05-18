package org.sas.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.User;
import org.sas.utils.HibernateUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class RoomDAO implements DAO<Room, Integer> {

    private final SessionFactory sessionFactory;

    public RoomDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(@NonNull  Room room) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(room);
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    @Nullable
    public Room read(@NonNull Integer id) {
        try (final Session session = sessionFactory.openSession()) {
            final Room result = session.get(Room.class, id);
            if (result != null) {
                Hibernate.initialize(result.getHouseId());
            }
            return result;
        }
    }

    @Override
    public void update(@NonNull Room room) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(room);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NonNull Room room) {
        try (final Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(room);
            session.getTransaction().commit();
        }
    }

    public List<Room> getRoomsList(Integer house_id) {
        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(house_id);
        try (final Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery(
                    "from org.sas.model.Room r where r.houseId = :houseId", Room.class);
            query.setParameter("houseId", house);
            return query.list();
        }
    }
}
