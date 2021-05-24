package org.sas;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.dao.HouseDAO;
import org.sas.dao.RoomDAO;
import org.sas.dao.UserDAO;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.User;

import static org.junit.Assert.*;

public class RoomDAOTest {
    private UserDAO userDAO;
    private HouseDAO houseDAO;
    private RoomDAO roomDAO;

    private final Room test_room = new Room();

    private final User test_user = new User();
    private final User test_user_2 = new User();

    private final House test_house = new House();
    private final House test_house_2 = new House();

    private int id;
    private int u1_id;
    private int u2_id;
    private int h1_id;
    private int h2_id;

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Before
    public void doBefore() {
        userDAO = new UserDAO(sessionFactory);
        houseDAO = new HouseDAO(sessionFactory);
        roomDAO = new RoomDAO(sessionFactory);

        test_user.setToken("token1");
        test_user.setSensorToken("sensortoken1");
        test_user.setPassword("09876");
        test_user.setTimeZone(7);
        test_user.setLogin("test_user_1");

        test_user_2.setToken("token2");
        test_user_2.setSensorToken("sensortoken2");
        test_user_2.setPassword("09876");
        test_user_2.setTimeZone(7);
        test_user_2.setLogin("test_user_2");

        u1_id = userDAO.create(test_user);
        u2_id = userDAO.create(test_user_2);

        test_house.setName("house1");
        test_house.setColor("red");
        test_house.setUserId(test_user);

        test_house_2.setName("house2");
        test_house_2.setColor("red");
        test_house_2.setUserId(test_user_2);

        h1_id = houseDAO.create(test_house);
        h2_id = houseDAO.create(test_house_2);

        test_room.setName("test_room");
        test_room.setColor("red");
        test_room.setHouseId(test_house);
    }

    @After
    public void doAfter() {
        if (roomDAO.read(id) != null) {
            roomDAO.delete(test_room);
        }
        if (houseDAO.read(h1_id) != null) {
            houseDAO.delete(test_house);
        }
        if (houseDAO.read(h2_id) != null) {
            houseDAO.delete(test_house_2);
        }
        if (userDAO.read(u1_id) != null) {
            userDAO.delete(test_user);
        }
        if (userDAO.read(u2_id) != null) {
            userDAO.delete(test_user_2);
        }

    }

    @Test
    public void testCreateRoom() {
        id = roomDAO.create(test_room);
        final Room room = roomDAO.read(id);
        assertNotNull(room);
        assertEquals(room, test_room);
    }

    @Test
    public void testUpdateRoom() {
        id = roomDAO.create(test_room);
        test_room.setColor("red");
        test_room.setName("new_name");
        test_room.setHouseId(test_house_2);
        roomDAO.update(test_room);

        final Room room = roomDAO.read(id);

        assertNotNull(room);

        assertTrue(room.getColor().equals("red")
                && room.getName().equals("new_name")
                && room.getHouseId().equals(test_house_2));
    }

    @Test
    public void testDeleteRoom() {
        id = roomDAO.create(test_room);

        final Room before_del_room = roomDAO.read(id);
        assertNotNull(before_del_room);

        roomDAO.delete(test_room);

        final Room after_del_room = roomDAO.read(id);
        assertNull(after_del_room);
    }
}
