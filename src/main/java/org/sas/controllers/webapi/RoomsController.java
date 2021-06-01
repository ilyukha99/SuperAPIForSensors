package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.dao.RoomDAO;
import org.sas.dao.SensorDAO;
import org.sas.dao.UserDAO;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.Sensor;
import org.sas.model.User;
import org.sas.views.RoomView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class RoomsController {
    private final HouseDAO houseDAO;
    private final RoomDAO roomDAO;
    private final SensorDAO sensorDAO;
    private final UserDAO userDAO;

    @Autowired
    public RoomsController(@NonNull HouseDAO houseDAO, @NonNull RoomDAO roomDAO,
                           @NonNull SensorDAO sensorDAO, @NonNull UserDAO userDAO) {
        this.houseDAO = houseDAO;
        this.roomDAO = roomDAO;
        this.sensorDAO = sensorDAO;
        this.userDAO = userDAO;
    }

    // TODO: здесь получаем user_id из куки
    private Integer getUserIdFromCookie(String userTokenHeader) { return userDAO.getUserIdByTokenHeader(userTokenHeader); }

    @GetMapping("/houses/{house_id}/rooms")
    public ResponseEntity<HashMap<String, Object>> getHouseRooms (@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id
    )
    {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        // check if user exists
        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


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
    public ResponseEntity<HashMap<String, Object>> getRoomByHouseId (@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id,
            @PathVariable Integer room_id
    ) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        // check if user exists
        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

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

    @PostMapping("/houses/{house_id}/rooms")
    public ResponseEntity<HashMap<String, Object>> createRoom (@PathVariable Integer house_id,
                                                               @RequestBody RoomView roomView,
                                                               @RequestHeader("Authorization") String userToken) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House house = houseDAO.read(house_id);

        // check if house exists
        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Room new_room = new Room();
        new_room.setName(roomView.getRoomName());
        new_room.setColor(roomView.getRoomColor());
        new_room.setHouseId(house);

        roomDAO.create(new_room);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/houses/{house_id}/rooms/{room_id}")
    public ResponseEntity<HashMap<String, Object>> deleteRoom (@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id,
            @PathVariable Integer room_id
    ) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House house = houseDAO.read(house_id);

        if (house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<HashMap<String, Object>> editRoom (@PathVariable Integer house_id,
                                                             @PathVariable Integer room_id,
                                                             @RequestBody RoomView roomView,
                                                             @RequestHeader("Authorization") String userToken) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House edit_house = houseDAO.read(house_id);

        if (edit_house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

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

        if (roomView.getRoomName() != null) {
            edit_room.setName(roomView.getRoomName());
        }
        if (roomView.getRoomColor() != null) {
            edit_room.setColor(roomView.getRoomColor());
        }

        roomDAO.update(edit_room);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
