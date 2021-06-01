package org.sas.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sas.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SensorDAOTest {
    private UserDAO userDAO;
    private HouseDAO houseDAO;
    private RoomDAO roomDAO;
    private SensorTypeDAO sensorTypeDAO;
    private SensorDAO sensorDAO;

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private final User testUser = new User();
    private final User testUser2 = new User();
    private final House testHouse = new House();
    private final Room testRoom = new Room();
    private final Room testRoom2 = new Room();
    private final SensorType testSensorType = new SensorType();
    private final SensorType testSensorType2 = new SensorType();

    private final Sensor testSensor = new Sensor();

    private int id;
    private int user1Id;
    private int user2Id;
    private int houseId;
    private int room1Id;
    private int room2Id;
    private int sensorType1Id;
    private int sensorType2Id;

    @Before
    public void doBefore() {
        userDAO = new UserDAO(sessionFactory);
        houseDAO = new HouseDAO(sessionFactory);
        roomDAO = new RoomDAO(sessionFactory);
        sensorTypeDAO = new SensorTypeDAO(sessionFactory);
        sensorDAO = new SensorDAO(sessionFactory);

        testUser.setToken("token1");
        testUser.setSensorToken("sensortoken1");
        testUser.setPassword("09876");
        testUser.setTimeZone(7);
        testUser.setLogin("test_user_1");

        testUser2.setToken("token2");
        testUser2.setSensorToken("sensortoken2");
        testUser2.setPassword("09876");
        testUser2.setTimeZone(7);
        testUser2.setLogin("test_user_2");

        user1Id = userDAO.create(testUser);
        user2Id = userDAO.create(testUser2);

        testHouse.setName("house1");
        testHouse.setColor("red");
        testHouse.setUserId(testUser);

        houseId = houseDAO.create(testHouse);

        testRoom.setName("test_room1");
        testRoom.setColor("red");
        testRoom.setHouseId(testHouse);

        testRoom2.setName("test_room2");
        testRoom2.setColor("yellow");
        testRoom2.setHouseId(testHouse);

        room1Id = roomDAO.create(testRoom);
        room2Id = roomDAO.create(testRoom2);

        testSensorType.setName("test_sensor_name");
        testSensorType.setDescription("sdhjskahgdlajshglasghlskaglksfaslkhgklsafhgkalsfgh");

        testSensorType2.setName("test_sensor_name_2");
        testSensorType2.setDescription("zxcursed");

        sensorType1Id = sensorTypeDAO.create(testSensorType);
        sensorType2Id = sensorTypeDAO.create(testSensorType2);

        testSensor.setType(testSensorType);
        testSensor.setUser(testUser);
        testSensor.setName("test_sensor1");
        testSensor.setRoomId(testRoom);
    }

    @After
    public void doAfter() {
        if (sensorDAO.read(id) != null) {
            sensorDAO.delete(testSensor);
        }
        if (sensorTypeDAO.read(sensorType1Id) != null) {
            sensorTypeDAO.delete(testSensorType);
        }
        if (sensorTypeDAO.read(sensorType2Id) != null) {
            sensorTypeDAO.delete(testSensorType2);
        }
        if (roomDAO.read(room1Id) != null) {
            roomDAO.delete(testRoom);
        }
        if (roomDAO.read(room2Id) != null) {
            roomDAO.delete(testRoom2);
        }
        if (houseDAO.read(houseId) != null) {
            houseDAO.delete(testHouse);
        }
        if (userDAO.read(user1Id) != null) {
            userDAO.delete(testUser);
        }
        if (userDAO.read(user2Id) != null) {
            userDAO.delete(testUser2);
        }
    }

    @Test
    public void testCreateSensor() {
        id = sensorDAO.create(testSensor);
        final Sensor sensor = sensorDAO.read(id);
        assertNotNull(sensor);
        assertEquals(sensor, testSensor);
    }

    @Test
    public void testUpdateSensor() {
        id = sensorDAO.create(testSensor);
        testSensor.setName("update_name");
        testSensor.setType(testSensorType2);
        testSensor.setUser(testUser2);
        testSensor.setRoomId(testRoom2);
        sensorDAO.update(testSensor);

        final Sensor sensor = sensorDAO.read(id);
        assertNotNull(sensor);
        assertTrue(sensor.getName().equals("update_name") && sensor.getUser().equals(testUser2)
                && sensor.getType().equals(testSensorType2) && sensor.getRoomId().equals(testRoom2));
    }

    @Test
    public void testDeleteSensor() {
        id = sensorDAO.create(testSensor);

        final Sensor sensorBeforeDelete = sensorDAO.read(id);
        assertNotNull(sensorBeforeDelete);

        sensorDAO.delete(testSensor);

        final Sensor sensorAfterDelete = sensorDAO.read(id);
        assertNull(sensorAfterDelete);

    }

    @Test
    public void testGetSensorsByHouseAndRoom() {
        id = sensorDAO.create(testSensor);
        final Sensor sensor = sensorDAO.read(id);
        assertNotNull(sensor);
        ArrayList<Sensor> sensors = (ArrayList<Sensor>) sensorDAO.getSensorsByHouseAndRoom(testHouse.getId(),
                sensor.getRoomId().getId());
        assertNotNull(sensors);
        assertEquals(sensor, sensors.get(0));
    }

    @Test
    public void testGetSensorsList() {
        id = sensorDAO.create(testSensor);
        final Sensor sensor = sensorDAO.read(id);
        assertNotNull(sensor);
        ArrayList<Sensor> sensors = (ArrayList<Sensor>) sensorDAO.getSensorsList(sensor.getRoomId().getId());
        assertNotNull(sensors);
        assertEquals(sensor, sensors.get(0));
    }

    @Test
    public void testGetSensorOwnerLogin() {
        id = sensorDAO.create(testSensor);
        final Sensor sensor = sensorDAO.read(id);
        assertNotNull(sensor);

        String login = sensorDAO.getSensorOwnerLogin(sensor.getId());
        assertNotNull(login);

        assertEquals(sensor.getUser().getLogin(), login);
    }
}
