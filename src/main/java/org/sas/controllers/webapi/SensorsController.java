package org.sas.controllers.webapi;

import org.sas.dao.*;
import org.sas.model.*;
import org.sas.responses.HttpResponse;
import org.sas.views.SensorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;


import java.util.ArrayList;
import java.util.Map;

@RestController
public class SensorsController {
    private final HouseDAO houseDAO;
    private final RoomDAO roomDAO;
    private final SensorDAO sensorDAO;
    private final SensorTypeDAO sensorTypeDAO;
    private final UserDAO userDAO;
    private final SensorDataDAO sensorDataDAO;

    public SensorsController(@NonNull HouseDAO houseDAO, @NonNull RoomDAO roomDAO, @NonNull SensorDAO sensorDAO,
                             @NonNull SensorTypeDAO sensorTypeDAO, @NonNull UserDAO userDAO,
                             @NonNull SensorDataDAO sensorDataDAO) {
        this.houseDAO = houseDAO;
        this.roomDAO = roomDAO;
        this.sensorDAO = sensorDAO;
        this.sensorTypeDAO = sensorTypeDAO;
        this.userDAO = userDAO;
        this.sensorDataDAO = sensorDataDAO;
    }

    @GetMapping("/houses/{houseId}/rooms/{roomId}/sensors")
    public ResponseEntity<Map<String, Object>> getSensors(@PathVariable int houseId, @PathVariable int roomId,
                                                          @RequestHeader("Authorization") String userTokenHeader) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            if (userDAO.getUserIdByTokenHeader(userTokenHeader) == house.getUserId().getId()) {
                ArrayList<Sensor> sensorList = (ArrayList<Sensor>) sensorDAO.getSensorsByHouseAndRoom(houseId, roomId);
                ArrayList<Integer> sensorsIdList = new ArrayList<>();
                for (Sensor sensor : sensorList) {
                    sensorsIdList.add(sensor.getId());
                }
                return new ResponseEntity<>(new HttpResponse(0, "")
                        .addResponseParameter("sensors", sensorsIdList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<Map<String, Object>> getSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                         @PathVariable int sensorId,
                                                         @RequestHeader("Authorization") String userTokenHeader) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            if (userDAO.getUserIdByTokenHeader(userTokenHeader) == house.getUserId().getId()) {
                Sensor sensor = sensorDAO.read(sensorId);
                if (sensor != null) {
                    return new ResponseEntity<>(new HttpResponse(0, "")
                            .addResponseParameter("name", sensor.getName()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new HttpResponse(2, "Invalid sensor")
                            .getResponse(),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/houses/{houseId}/rooms/{roomId}/sensors")
    public ResponseEntity<Map<String, Object>> createSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                            @RequestBody SensorView sensorView,
                                                            @RequestHeader("Authorization") String userTokenHeader) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            Integer userId = userDAO.getUserIdByTokenHeader(userTokenHeader);
            if (userId == house.getUserId().getId()) {
                House houseOfRoom = houseDAO.read(room.getHouseId().getId());
                SensorType sensorType = sensorTypeDAO.read(sensorView.getTypeId());
                if (houseOfRoom != null && sensorType != null && houseOfRoom.equals(house)) {
                    Sensor sensor = new Sensor();
                    sensor.setName(sensorView.getName());
                    sensor.setUser(userDAO.read(userId));
                    sensor.setType(sensorTypeDAO.read(sensorView.getTypeId()));
                    sensor.setRoomId(room);
                    sensorDAO.create(sensor);
                    return new ResponseEntity<>(new HttpResponse(0, "").getResponse(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            new HttpResponse(1, "The specified room does not belong to this house or" +
                                    " sensor type is invalid")
                                    .getResponse(),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<Map<String, Object>> updateSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                            @PathVariable int sensorId,
                                                            @RequestBody SensorView sensorView,
                                                            @RequestHeader("Authorization") String userTokenHeader) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            if (userDAO.getUserIdByTokenHeader(userTokenHeader) == house.getUserId().getId()) {
                Sensor sensor = sensorDAO.read(sensorId);
                SensorType sensorType = sensorTypeDAO.read(sensorView.getTypeId());
                if (sensor != null && sensorType != null) {
                    sensor.setName(sensorView.getName());
                    sensor.setType(sensorType);
                    sensorDAO.update(sensor);
                    return new ResponseEntity<>(new HttpResponse(0, "").getResponse(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new HttpResponse(2, "Sensor with given id doesn't exist" +
                            " or sensor type is invalid")
                            .getResponse(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/houses/{houseId}/rooms/{roomId}/sensors/{sensorId}")
    public ResponseEntity<Map<String, Object>> deleteSensor(@PathVariable int houseId, @PathVariable int roomId,
                                                            @PathVariable int sensorId,
                                                            @RequestHeader("Authorization") String userTokenHeader) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);

        if (house != null && room != null) {
            if (userDAO.getUserIdByTokenHeader(userTokenHeader) == house.getUserId().getId()) {
                Sensor sensor = sensorDAO.read(sensorId);
                if (sensor != null) {
                    sensorDAO.delete(sensor);
                    return new ResponseEntity<>(new HttpResponse(0, "").getResponse(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new HttpResponse(2, "Sensor with given id doesn't exist")
                            .getResponse(),
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
