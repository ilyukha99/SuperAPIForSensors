package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.dao.RoomDAO;
import org.sas.dao.SensorDAO;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.Sensor;
import org.sas.utils.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class RoomsController {

    // TODO: здесь получаем user_id из куки
    private Integer getUserIdFromCookie() { return 1; }

    @GetMapping("/houses/{house_id}/rooms")
    public ResponseEntity<HashMap<String, Object>> getHouseRooms (
            @PathVariable Integer house_id
    )
    {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        // check if user exists
        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());

        ArrayList<Room> roomsList = (ArrayList<Room>) roomDAO.getRoomsList(house_id);
        ArrayList<Integer> resp = new ArrayList<>();

        for (Room room : roomsList) {
            resp.add(room.getId());
        }
        response.put("rooms", resp);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/houses/{house_id}/rooms/{room_id}")
    public ResponseEntity<HashMap<String, Object>> getRoomByHouseId (
            @PathVariable Integer house_id,
            @PathVariable Integer room_id
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        // check if user exists
        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        Room room = roomDAO.read(room_id);

        // check if room exists
        if (room == null) {
            response.put("error", String.format("room with id %d does not exist", room_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // check if requested room is in the house_id
        if (room.getHouseId().getId() != house_id) {
            response.put("error", String.format("room with id %d does not exist in the house %d", room_id, house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());

        ArrayList<Sensor> sensorsList = (ArrayList<Sensor>) sensorDAO.getSensorsList(room_id);
        ArrayList<Integer> resp = new ArrayList<>();

        for (Sensor sensor : sensorsList) {
            resp.add(sensor.getId());
        }
        response.put("sensors", resp);

        response.put("error", "");
        response.put("code", 0);

        response.put("room_name", room.getName());
        response.put("room_color", room.getColor());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // EXAMPLE: http://localhost:8081/houses/1/rooms?name=room_01&color=orange
    @PostMapping("/houses/{house_id}/rooms")
    public ResponseEntity<HashMap<String, Object>> createRoom (
            @PathVariable Integer house_id,
            @RequestParam(value = "name", required=false) String room_name,
            @RequestParam(value = "color", required=false) String room_color
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Room new_room = new Room();
        new_room.setName(room_name);
        new_room.setColor(room_color);
        new_room.setHouseId(house);

        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        roomDAO.create(new_room);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/houses/{house_id}/rooms/{room_id}")
    public ResponseEntity<HashMap<String, Object>> deleteRoom (
            @PathVariable Integer house_id,
            @PathVariable Integer room_id
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(house_id);

        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        Room del_room = roomDAO.read(room_id);

        // check if room exists
        if (del_room == null) {
            response.put("error", String.format("room with id %d does not exist", room_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // check if requested room is in the house_id
        if (del_room.getHouseId().getId() != house_id) {
            response.put("error", String.format("room with id %d does not exist in the house %d", room_id, house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        roomDAO.delete(del_room);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/houses/{house_id}/rooms/{room_id}")
    public ResponseEntity<HashMap<String, Object>> editRoom (
            @PathVariable Integer house_id,
            @PathVariable Integer room_id,
            @RequestParam(value = "color", required = false)  String color,
            @RequestParam(value = "name", required = false)  String name
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House edit_house = houseDAO.read(house_id);

        if (edit_house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        RoomDAO roomDAO = new RoomDAO(HibernateUtils.getSessionFactory());
        Room edit_room = roomDAO.read(room_id);

        // check if room exists
        if (edit_room == null) {
            response.put("error", String.format("room with id %d does not exist", room_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // check if requested room is in the house_id
        if (edit_room.getHouseId().getId() != house_id) {
            response.put("error", String.format("room with id %d does not exist in the house %d", room_id, house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        if (name != null) {
            edit_room.setName(name);
        }
        if (color != null) {
            edit_room.setColor(color);
        }

        roomDAO.update(edit_room);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
