package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.dao.UserDAO;
import org.sas.model.House;
import org.sas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HousesController {
    private final HouseDAO houseDAO;
    private final UserDAO userDAO;

    @Autowired
    public HousesController(@NonNull HouseDAO houseDAO, @NonNull UserDAO userDAO) {
        this.houseDAO = houseDAO;
        this.userDAO = userDAO;
    }

    // TODO: здесь получаем user_id из куки
    private Integer getUserIdFromCookie(String userToken) { return userDAO.getUserIdByToken(userToken); }

    @GetMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> getUserHouses(@RequestHeader("Authorization") String userToken)
    {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        ArrayList<House> housesList = (ArrayList<House>) houseDAO.getHousesList(user_id);
        ArrayList<Integer> resp = new ArrayList<>();

        for (House house : housesList) {
            resp.add(house.getId());
        }
        response.put("houses", resp);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> getUserHouseById(@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id
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

        response.put("error", "");
        response.put("code", 0);

        response.put("house_name", house.getName());
        response.put("house_color", house.getColor());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // EXAMPLE: http://localhost:8081/houses?name=temp_02&color=orange
    @PostMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> createHouse (@RequestHeader("Authorization") String userToken,
            @RequestParam(value = "name", required=false) String house_name,
            @RequestParam(value = "color", required=false) String house_color
    ) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        User user = userDAO.read(user_id);

        House new_house = new House();
        new_house.setName(house_name);
        new_house.setColor(house_color);
        new_house.setUserId(user);

        houseDAO.create(new_house);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> deleteHouse(@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id
    ) {
        Integer user_id = getUserIdFromCookie(userToken);

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House del_house = houseDAO.read(house_id);

        if (del_house == null) {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        houseDAO.delete(del_house);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> editHouse(@RequestHeader("Authorization") String userToken,
            @PathVariable Integer house_id,
            @RequestParam(value = "color", required = false)  String color,
            @RequestParam(value = "name", required = false)  String name
    ) {
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

        if (name != null) {
            edit_house.setName(name);
        }
        if (color != null) {
            edit_house.setColor(color);
        }

        houseDAO.update(edit_house);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
