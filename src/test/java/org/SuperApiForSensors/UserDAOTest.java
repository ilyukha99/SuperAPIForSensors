package org.SuperApiForSensors;

import org.SuperApiForSensors.dao.UserDAO;
import org.SuperApiForSensors.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;

import static org.junit.Assert.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private SessionFactory sessionFactory;
    private final User testUser = new User();
    private final Integer primaryKey = 100500;

    @Before
    public void doBefore() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        userDAO = new UserDAO(sessionFactory);
        testUser.setId(primaryKey);
        testUser.setToken("qwerty");
        testUser.setPassword("12345");
        testUser.setTimezone(18);
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
     * @see org.SuperApiForSensors.dao.UserDAO#create(User)
     * @see org.SuperApiForSensors.dao.UserDAO#read(Integer)
     */
    @Test
    public void checkCreatedUser() {
        userDAO.create(testUser);
        final User user = userDAO.read(primaryKey);
        assertEquals(user, testUser);
    }

    /**
     * @see org.SuperApiForSensors.dao.UserDAO#delete(User)
     */
    @Test
    public void checkDeletions() {
        userDAO.create(testUser);
        final User userBeforeDeletion = userDAO.read(primaryKey);
        assertNotNull(userBeforeDeletion.getLogin());
        userDAO.delete(testUser);
        final User userAfterDeletion = userDAO.read(primaryKey);
        assertNull(userAfterDeletion.getLogin());
    }

    /**
     * @see org.SuperApiForSensors.dao.UserDAO#update(User)
     */
    @Test
    public void checkUpdates() {
        userDAO.create(testUser);
        testUser.setLogin("NewLogin");
        testUser.setPassword("NewPassword");
        userDAO.update(testUser);
        final User user = userDAO.read(primaryKey);
        assertTrue(user.getLogin().equals("NewLogin") && user.getPassword().equals("NewPassword"));
    }
}
