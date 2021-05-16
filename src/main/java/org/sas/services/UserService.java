package org.sas.services;

import org.hibernate.Hibernate;
import org.sas.dao.UserDAO;
import org.sas.model.User;
import org.sas.repositories.UserRepository;
import org.sas.utils.HibernateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new UsernameNotFoundException("No user found");
    }

    public void updateToken(String token, String login) {
        User user = userRepository.findByLogin(login);
        user.setToken(token);
        new UserDAO(HibernateUtils.getSessionFactory()).update(user);
    }
}
