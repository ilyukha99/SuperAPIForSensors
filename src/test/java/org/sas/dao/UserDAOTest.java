package org.sas.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.model.User;

import static org.junit.Assert.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final User testUser = new User();
    private int primaryKey;
    private final String userLogin = "test";

    @Before
    public void doBefore() {
        userDAO = new UserDAO(sessionFactory);
        testUser.setToken("qwerty");
        testUser.setSensorToken("fdsgdfshf");
        testUser.setPassword("12345");
        testUser.setTimeZone(18);
        testUser.setSensorToken("dfaf");
        testUser.setLogin(userLogin);
    }

    @After
    public void doAfter() {
        if (userDAO.read(primaryKey) != null) {
            userDAO.delete(testUser);
        }
    }

    /**
     * @see org.sas.dao.UserDAO#create(User)
     * @see org.sas.dao.UserDAO#read(Integer)
     */
    @Test
    public void checkUserCreation() {
        primaryKey = userDAO.create(testUser);
        final User user = userDAO.read(primaryKey);
        assertNotNull(user);
        assertEquals(user, testUser);
    }

    /**
     * @see org.sas.dao.UserDAO#delete(User)
     */
    @Test
    public void checkUserDeletion() {
        primaryKey = userDAO.create(testUser);
        final User userBeforeDeletion = userDAO.read(primaryKey);
        assertNotNull(userBeforeDeletion);
        userDAO.delete(testUser);
        final User userAfterDeletion = userDAO.read(primaryKey);
        assertNull(userAfterDeletion);
    }

    /**
     * @see org.sas.dao.UserDAO#update(User)
     */
    @Test
    public void checkUserUpdate() {
        primaryKey = userDAO.create(testUser);
        testUser.setLogin("NewLogin");
        testUser.setPassword("NewPassword");
        userDAO.update(testUser);
        final User user = userDAO.read(primaryKey);
        assertNotNull(user);
        assertTrue(user.getLogin().equals("NewLogin") && user.getPassword().equals("NewPassword"));
    }

    /**
     * @see org.sas.dao.UserDAO#findByLogin(String)
     * @see org.sas.dao.UserDAO#loginExists(String)
     */
    @Test
    public void checkAndFindByLogin() {
        primaryKey = userDAO.create(testUser);
        assertTrue(userDAO.loginExists(userLogin));
        assertEquals(userDAO.findByLogin(userLogin), testUser);
    }

    /**
     * @see org.sas.dao.UserDAO#tokenExists(String)
     * @see org.sas.dao.UserDAO#getUserIdByTokenHeader(String)
     */
    @Test
    public void checkUserLoginAndFindByHeader() {
        primaryKey = userDAO.create(testUser);
        String token = testUser.getToken();
        assertTrue(userDAO.tokenExists(token));
        assertEquals(primaryKey, userDAO.getUserIdByTokenHeader("Bearer " + token).intValue());
    }

    /**
     * @see org.sas.dao.UserDAO#sensorTokenExists(String)
     */
    @Test
    public void checkSensorTokenExistence() {
        primaryKey = userDAO.create(testUser);
        assertTrue(userDAO.sensorTokenExists(testUser.getSensorToken()));
        assertFalse(userDAO.sensorTokenExists("|||||||||||||||||||"));
    }
}
