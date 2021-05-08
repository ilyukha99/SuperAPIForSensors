package org.sas;

import org.hibernate.Hibernate;
import org.sas.dao.*;
import org.sas.model.Sensor;
import org.sas.model.SensorData;
import org.sas.model.SensorType;
import org.sas.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.sas.utils.HibernateUtils;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtils.getSessionFactory()) {
            DAO<User, Integer> userDAO = new UserDAO(sessionFactory);
            DAO<SensorType, Integer> sensorTypeDAO = new SensorTypeDAO(sessionFactory);
//
//            final User user = new User();
//            user.setLogin("ilya567");
//            user.setPassword("pswd123");
//            user.setTimezone(7);
//            user.setToken("kkfkfk");
//            dao.create(user);
//
//            final User result = dao.read(0);
//            assert result != null;
//            System.err.println("Read: " + result);
//
//            result.setToken("k1k1k1k1");
//            dao.update(result);
//
//            dao.delete(result);
            DAO<Sensor, Integer> sensorDAO = new SensorDAO(sessionFactory);
            SensorDataDAO sensorDataIntegerDAO = new SensorDataDAO(sessionFactory);

//            final Sensor sensor = new Sensor();
//            sensor.setUser(userDAO.read(1));
//            //sensor.setType(sensorTypeDAO.read(1));
//            SensorType newType = new SensorType();
//            newType.setName("eee");
//            sensor.setType(newType);
//            sensor.setName("stass");
//            sensorDAO.create(sensor);
            List list = sensorDataIntegerDAO.getSensorDataByDate(new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis() + 1000000));
            System.out.println(list);
        }
        catch (org.hibernate.HibernateException exception) {
            exception.printStackTrace();
        }
    }
}
