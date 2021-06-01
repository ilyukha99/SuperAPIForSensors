package org.sas.services;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.dao.UserDAO;
import org.sas.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserDAO userDAO = new UserDAO(new Configuration().configure().buildSessionFactory());
    private final User testUser = new User();
    private static final String originalPassword = "12345";
    private static final String userLogin = "test";

    @Before
    public void doBefore() {
        userService = new UserService(passwordEncoder, userDAO);
        testUser.setToken("qwerty");
        testUser.setSensorToken("fdsgdfshf");
        testUser.setPassword(originalPassword);
        testUser.setTimeZone(18);
        testUser.setSensorToken("dfaf");
        testUser.setLogin(userLogin);
    }

    @After
    public void doAfter() {
        if (userService.findByLogin(userLogin) != null) {
            userDAO.delete(testUser);
        }
    }

    /**
     * @see org.sas.services.UserService#saveNewUser(User)
     * @see org.sas.services.UserService#findByLogin(String)
     * @see org.sas.services.UserService#findByLoginAndPassword(String, String)
     */
    @Test
    public void testUserCreationAndSearching() {
        userService.saveNewUser(testUser);
        assertEquals(userService.findByLogin(userLogin), testUser);
        assertEquals(userService.findByLoginAndPassword(userLogin, originalPassword), testUser);
    }

    /**
     * @see org.sas.services.UserService#updateSensorToken(String, String)
     * @see org.sas.services.UserService#updateUserToken(String, String)
     */
    @Test
    public void testUserTokensChanging() {
        userService.saveNewUser(testUser);
        String newUserToken = "abcde";
        String newUserSensorToken = "abcdef";
        userService.updateSensorToken(newUserSensorToken, userLogin);
        userService.updateUserToken(newUserToken, userLogin);
        User newUser = userService.findByLogin(userLogin);
        assertTrue(newUser.getToken().equals(newUserToken) &&
                newUser.getSensorToken().equals(newUserSensorToken));
    }
}
