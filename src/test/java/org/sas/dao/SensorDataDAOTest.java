package org.sas.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.model.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SensorDataDAOTest {
    private UserDAO userDAO;
    private HouseDAO houseDAO;
    private RoomDAO roomDAO;
    private SensorTypeDAO sensorTypeDAO;
    private SensorDAO sensorDAO;
    private SensorDataDAO sensorDataDAO;

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private final User testUser = new User();
    private final House testHouse = new House();
    private final Room testRoom = new Room();
    private final SensorType testSensorType = new SensorType();
    private final Sensor testSensor1 = new Sensor();
    private final Sensor testSensor2 = new Sensor();

    private final SensorData testSensorData = new SensorData();

    private int id;
    private int user1Id;
    private int houseId;
    private int room1Id;
    private int sensorType1Id;
    private int sensor1Id;
    private int sensor2Id;

    @Before
    public void doBefore() {
        userDAO = new UserDAO(sessionFactory);
        houseDAO = new HouseDAO(sessionFactory);
        roomDAO = new RoomDAO(sessionFactory);
        sensorTypeDAO = new SensorTypeDAO(sessionFactory);
        sensorDAO = new SensorDAO(sessionFactory);
        sensorDataDAO = new SensorDataDAO(sessionFactory);

        testUser.setToken("token1");
        testUser.setSensorToken("sensortoken1");
        testUser.setPassword("09876");
        testUser.setTimeZone(7);
        testUser.setLogin("test_user_1");

        user1Id = userDAO.create(testUser);

        testHouse.setName("house1");
        testHouse.setColor("red");
        testHouse.setUserId(testUser);

        houseId = houseDAO.create(testHouse);

        testRoom.setName("test_room1");
        testRoom.setColor("red");
        testRoom.setHouseId(testHouse);

        room1Id = roomDAO.create(testRoom);

        testSensorType.setName("test_sensor_name");
        testSensorType.setDescription("sdhjskahgdlajshglasghlskaglksfaslkhgklsafhgkalsfgh");

        sensorType1Id = sensorTypeDAO.create(testSensorType);

        testSensor1.setType(testSensorType);
        testSensor1.setUser(testUser);
        testSensor1.setName("test_sensor1");
        testSensor1.setRoomId(testRoom);

        testSensor2.setType(testSensorType);
        testSensor2.setUser(testUser);
        testSensor2.setName("test_sensor2");
        testSensor2.setRoomId(testRoom);

        sensor1Id = sensorDAO.create(testSensor1);
        sensor2Id = sensorDAO.create(testSensor2);

        testSensorData.setSensor(testSensor1);
        testSensorData.setTime(new Timestamp(100));
        testSensorData.setValue(100.13f);
    }

    @After
    public void doAfter() {
        if (sensorDataDAO.read(id) != null) {
            sensorDataDAO.delete(testSensorData);
        }
        if (sensorDAO.read(sensor1Id) != null) {
            sensorDAO.delete(testSensor1);
        }
        if (sensorDAO.read(sensor2Id) != null) {
            sensorDAO.delete(testSensor2);
        }
        if (sensorTypeDAO.read(sensorType1Id) != null) {
            sensorTypeDAO.delete(testSensorType);
        }
        if (roomDAO.read(room1Id) != null) {
            roomDAO.delete(testRoom);
        }
        if (houseDAO.read(houseId) != null) {
            houseDAO.delete(testHouse);
        }
        if (userDAO.read(user1Id) != null) {
            userDAO.delete(testUser);
        }
    }

    @Test
    public void testCreateSensor() {
        id = sensorDataDAO.create(testSensorData);
        final SensorData sensorData = sensorDataDAO.read(id);
        assertNotNull(sensorData);
        assertEquals(sensorData, testSensorData);
    }

    @Test
    public void testUpdateSensor() {
        id = sensorDataDAO.create(testSensorData);
        testSensorData.setSensor(testSensor2);
        testSensorData.setValue(123.123f);
        testSensorData.setTime(new Timestamp(200));
        sensorDataDAO.update(testSensorData);

        final SensorData sensorData = sensorDataDAO.read(id);
        assertNotNull(sensorData);
        assertTrue(sensorData.getSensor().equals(testSensor2)
                && sensorData.getTime().equals(new Timestamp(200)) && sensorData.getValue() == 123.123f);
    }

    @Test
    public void testDeleteSensor() {
        id = sensorDataDAO.create(testSensorData);

        final SensorData sensorDataBeforeDelete = sensorDataDAO.read(id);
        assertNotNull(sensorDataBeforeDelete);

        sensorDataDAO.delete(testSensorData);

        final SensorData sensorDataAfterDelete = sensorDataDAO.read(id);
        assertNull(sensorDataAfterDelete);

    }

    @Test
    public void testGetSensorDataByDate() {
        id = sensorDataDAO.create(testSensorData);

        final SensorData sensorData = sensorDataDAO.read(id);
        assertNotNull(sensorData);

        ArrayList<SensorData> sensorDataList1 = (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(null, null);
        assertNotNull(sensorDataList1);
        assertTrue(sensorDataList1.contains(sensorData));

        ArrayList<SensorData> sensorDataList2 = (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(null, 200L);
        assertNotNull(sensorDataList2);
        assertTrue(sensorDataList2.contains(sensorData));

        ArrayList<SensorData> sensorDataList3 = (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(15L, null);
        assertNotNull(sensorDataList3);
        assertTrue(sensorDataList3.contains(sensorData));

        ArrayList<SensorData> sensorDataList4 = (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(0L, 0L);
        assertNotNull(sensorDataList4);
        assertFalse(sensorDataList4.contains(sensorData));

        ArrayList<SensorData> sensorDataList5 = (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(0L, 101L);
        assertNotNull(sensorDataList5);
        assertTrue(sensorDataList5.contains(sensorData));
    }
}
