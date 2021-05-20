package org.sas.services;

import org.sas.dao.UserDAO;
import org.sas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@NonNull PasswordEncoder passwordEncoder, @NonNull UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    public int saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.create(user);
    }

    public User findByLogin(String login) {
        User user = userDAO.findByLogin(login);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("No user found");
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = userDAO.findByLogin(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new UsernameNotFoundException("No user found");
    }

    public void updateUserToken(String token, String login) {
        User user = userDAO.findByLogin(login);
        user.setToken(token);
        userDAO.update(user);
    }

    public void updateSensorToken(String sensorToken, String login) {
        User user = userDAO.findByLogin(login);
        user.setSensorToken(sensorToken);
        userDAO.update(user);
    }
}
