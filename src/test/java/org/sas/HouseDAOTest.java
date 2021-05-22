package org.sas;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.dao.HouseDAO;
import org.sas.dao.UserDAO;
import org.sas.model.House;
import org.sas.model.User;

import static org.junit.Assert.*;

public class HouseDAOTest {
    private UserDAO userDAO;
    private HouseDAO houseDAO;

    private final House test_house = new House();
    private final User test_user = new User();
    private final User test_user_2 = new User();

    private int id;
    private int u1_id;
    private int u2_id;

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Before
    public void doBefore() {
        userDAO = new UserDAO(sessionFactory);
        houseDAO = new HouseDAO(sessionFactory);

        test_user.setToken("qwerty");
        test_user.setSensorToken("token");
        test_user.setPassword("12345");
        test_user.setTimeZone(7);
        test_user.setLogin("test_user");

        test_user_2.setToken("qwertyqwerty");
        test_user_2.setSensorToken("tokentoken");
        test_user_2.setPassword("12345");
        test_user_2.setTimeZone(7);
        test_user_2.setLogin("test_user_2");

        u1_id = userDAO.create(test_user);
        u2_id = userDAO.create(test_user_2);

        test_house.setName("test_house");
        test_house.setColor("blue");
        test_house.setUserId(test_user);
    }

    @After
    public void doAfter() {
        if (houseDAO.read(id) != null) {
            houseDAO.delete(test_house);
        }
        if (userDAO.read(u1_id) != null) {
            userDAO.delete(test_user);
        }
        if (userDAO.read(u2_id) != null) {
            userDAO.delete(test_user_2);
        }
    }

    @Test
    public void testCreateHouse() {
        id = houseDAO.create(test_house);
        final House new_house = houseDAO.read(id);
        assertNotNull(new_house);
        assertEquals(new_house, test_house);
    }

    @Test
    public void testUpdateHouse() {
        id = houseDAO.create(test_house);
        test_house.setColor("red");
        test_house.setName("new_name");
        test_house.setUserId(test_user_2);
        houseDAO.update(test_house);

        final House house = houseDAO.read(id);

        assertNotNull(house);

        assertTrue(house.getColor().equals("red")
                && house.getName().equals("new_name")
                && house.getUserId().equals(test_user_2));
    }

    @Test
    public void testDeleteHouse() {
        id = houseDAO.create(test_house);

        final House before_del_house = houseDAO.read(id);
        assertNotNull(before_del_house);

        houseDAO.delete(test_house);

        final House after_del_house = houseDAO.read(id);
        assertNull(after_del_house);
    }
}
