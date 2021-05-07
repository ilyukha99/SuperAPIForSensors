package org.sas;

import org.sas.dao.DAO;
import org.sas.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.sas.dao.UserDAO;

public class Application {

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
            DAO<User, Integer> dao = new UserDAO(sessionFactory);

            final User user = new User();
            user.setLogin("ilya567");
            user.setPassword("pswd123");
            user.setTimezone(7);
            user.setToken("kkfkfk");
            dao.create(user);

            final User result = dao.read(0);
            System.out.println("Read: " + result.toString());

            result.setToken("k1k1k1k1");
            dao.update(result);

            dao.delete(result);
        }
        catch (org.hibernate.HibernateException exception) {
            exception.printStackTrace();
        }
    }
}
