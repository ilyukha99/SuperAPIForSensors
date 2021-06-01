package org.sas.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.model.SensorType;

import static org.junit.Assert.*;

public class SensorTypeDAOTest {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private SensorTypeDAO sensorTypeDAO;
    private final SensorType testSensorType = new SensorType();
    private int primaryKey;

    @Before
    public void doBefore() {
        sensorTypeDAO = new SensorTypeDAO(sessionFactory);
        testSensorType.setName("electromagnetic");
        testSensorType.setDescription("Just a typical sensor type... Nothing special.");
    }

    @After
    public void doAfter() {
        if (sensorTypeDAO.read(primaryKey) != null) {
            sensorTypeDAO.delete(testSensorType);
        }
    }

    /**
     * @see org.sas.dao.SensorTypeDAO#create(SensorType)
     * @see org.sas.dao.SensorTypeDAO#read(Integer)
     */
    @Test
    public void checkSensorTypeCreation() {
        primaryKey = sensorTypeDAO.create(testSensorType);
        final SensorType sensorType = sensorTypeDAO.read(primaryKey);
        assertNotNull(sensorType);
        assertEquals(sensorType, testSensorType);
    }

    /**
     * @see org.sas.dao.SensorTypeDAO#delete(SensorType)
     */
    @Test
    public void checkSensorTypeDeletion() {
        primaryKey = sensorTypeDAO.create(testSensorType);
        final SensorType sensorTypeBeforeDeletion = sensorTypeDAO.read(primaryKey);
        assertNotNull(sensorTypeBeforeDeletion);
        sensorTypeDAO.delete(testSensorType);
        final SensorType sensorTypeAfterDeletion = sensorTypeDAO.read(primaryKey);
        assertNull(sensorTypeAfterDeletion);
    }

    /**
     * @see org.sas.dao.SensorTypeDAO#update(SensorType)
     */
    @Test
    public void checkSensorTypeUpdates() {
        primaryKey = sensorTypeDAO.create(testSensorType);
        testSensorType.setName("weather");
        testSensorType.setDescription("Interesting type of sensors.");
        sensorTypeDAO.update(testSensorType);
        SensorType sensorType = sensorTypeDAO.read(primaryKey);
        assertNotNull(sensorType);
        assertEquals(sensorType, testSensorType);
    }
}
