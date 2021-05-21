package org.sas.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sas.model.House;
import org.sas.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomDAO implements DAO<Room, Integer> {
    private final SessionFactory sessionFactory;
    private HouseDAO houseDAO;

    @Autowired
    public RoomDAO(@NonNull final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setHouseDAO(@NonNull HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
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

    public List<Room> getRoomsList(Integer houseId) {
        House house = houseDAO.read(houseId);
        try (final Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery(
                    "from org.sas.model.Room r where r.houseId = :houseId", Room.class);
            query.setParameter("houseId", house);
            return query.list();
        }
    }
}
