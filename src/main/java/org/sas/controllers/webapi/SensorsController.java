package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.dao.RoomDAO;
import org.sas.dao.SensorDAO;
import org.sas.dao.SensorTypeDAO;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.Sensor;
import org.sas.utils.HibernateUtils;
import org.sas.views.SensorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;


import java.util.ArrayList;

@RestController
public class SensorsController {
    private final HouseDAO houseDAO;

    public SensorsController(@NonNull HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @GetMapping("/houses/{houseId}/rooms/{roomId}/sensors")
    public ResponseEntity<HttpResponse> getSensors(@PathVariable int houseId, @PathVariable int roomId) {

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());

        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            ArrayList<Sensor> sensorList = (ArrayList<Sensor>) sensorDAO.getSensorsByHouseAndRoom(houseId, roomId);
            ArrayList<Integer> sensorsIdList = new ArrayList<>();
            for (Sensor sensor: sensorList) {
                sensorsIdList.add(sensor.getId());
            }
            HttpResponse httpResponse = new HttpResponse(0, "");
            httpResponse.putElement("sensors", sensorsIdList);
            return new ResponseEntity<>(httpResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<HttpResponse> getSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                  @PathVariable int sensorId) {
        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());

        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            Sensor sensor = sensorDAO.read(sensorId);
            if (sensor != null) {
                HttpResponse httpResponse = new HttpResponse(0, "");
                httpResponse.putElement("name", sensor.getName());
                return new ResponseEntity<>(httpResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HttpResponse(2, "Invalid sensor"),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/houses/{houseId}/rooms/{roomId}/sensors")
    public ResponseEntity<HttpResponse> createSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                     @RequestBody SensorView sensorView) {
        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());
        SensorTypeDAO sensorTypeDAO = new SensorTypeDAO(HibernateUtils.getSessionFactory());

        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            Sensor sensor = new Sensor();
            sensor.setName(sensorView.getName());
            //TODO:need cookie
            sensor.setUser(house.getUserId());
            sensor.setType(sensorTypeDAO.read(sensorView.getTypeId()));
            sensor.setRoomId(room);
            sensorDAO.create(sensor);
            return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<HttpResponse> updateSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                     @PathVariable int sensorId, @RequestBody SensorView sensorView) {
        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());
        SensorTypeDAO sensorTypeDAO = new SensorTypeDAO(HibernateUtils.getSessionFactory());

        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            Sensor sensor = sensorDAO.read(sensorId);
            if (sensor != null) {
                sensor.setName(sensorView.getName());
                sensor.setType(sensorTypeDAO.read(sensorView.getTypeId()));
                sensorDAO.update(sensor);
                return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HttpResponse(2, "Sensor with given id doesn't exist"),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<HttpResponse> deleteSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                     @PathVariable int sensorId) {
        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());

        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            Sensor sensor = sensorDAO.read(sensorId);
            if (sensor != null) {
                sensorDAO.delete(sensor);
                return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HttpResponse(2, "Sensor with given id doesn't exist"),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
