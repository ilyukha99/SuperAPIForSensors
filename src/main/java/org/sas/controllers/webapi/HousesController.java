package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.model.House;
import org.sas.utils.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HousesController {

    // TODO: здесь получаем user_id из куки
    private Integer getUserIdFromCookie() { return 0; }

    @GetMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> getUserHouses()
    {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        ArrayList<House> housesList = (ArrayList<House>) houseDAO.getHousesList(user_id);

        response.put("houses", housesList);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> getUserHouseById(
            @PathVariable @RequestParam int house_id
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

        response.put("error", "");
        response.put("code", 0);

        response.put("house_name", house.getName());
        response.put("house_color", house.getColor());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> createHouse (
            @RequestParam String house_name, @RequestParam String house_color
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        House new_house = new House();
        new_house.setName(house_name);
        new_house.setColor(house_color);
        new_house.setUserId(user_id);

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        houseDAO.create(new_house);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> deleteHouse(
            @PathVariable @RequestParam int house_id
    ) {
        Integer user_id = getUserIdFromCookie();

        HashMap<String, Object> response = new HashMap<>();

        if (user_id == null) {
            response.put("error", "unauthorized user");
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
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
    public ResponseEntity<HashMap<String, Object>> editHouse(
            @PathVariable @RequestParam int house_id,
            @RequestParam String color,
            @RequestParam String name
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

        edit_house.setName(name);
        edit_house.setColor(color);
        houseDAO.update(edit_house);

        response.put("error", "");
        response.put("code", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
