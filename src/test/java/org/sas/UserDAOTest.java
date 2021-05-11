package org.sas;

import org.sas.dao.UserDAO;
import org.sas.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;

import static org.junit.Assert.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private SessionFactory sessionFactory;
    private final User testUser = new User();
    private int primaryKey;

    @Before
    public void doBefore() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        userDAO = new UserDAO(sessionFactory);
        testUser.setToken("qwerty");
        testUser.setPassword("12345");
        testUser.setTimeZone(18);
        testUser.setLogin("test");
    }

    @After
    public void doAfter() {
        if (userDAO.read(primaryKey) != null) {
            userDAO.delete(testUser);
        }
        sessionFactory.close();
    }

    /**
     * @see org.sas.dao.UserDAO#create(User)
     * @see org.sas.dao.UserDAO#read(Integer)
     */
    @Test
    public void checkCreatedUser() {
        primaryKey = userDAO.create(testUser);
        final User user = userDAO.read(primaryKey);
        assertNotNull(user);
        assertEquals(user, testUser);
    }

    /**
     * @see org.sas.dao.UserDAO#delete(User)
     */
    @Test
    public void checkDeletions() {
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
    public void checkUpdates() {
        primaryKey = userDAO.create(testUser);
        testUser.setLogin("NewLogin");
        testUser.setPassword("NewPassword");
        userDAO.update(testUser);
        final User user = userDAO.read(primaryKey);
        assertNotNull(user);
        assertTrue(user.getLogin().equals("NewLogin") && user.getPassword().equals("NewPassword"));
    }
}
