package org.sas.controllers.webapi;

import com.sun.xml.bind.v2.TODO;
import org.sas.dao.HouseDAO;
import org.sas.dao.SensorDataDAO;
import org.sas.model.House;
import org.sas.model.SensorData;
import org.sas.utils.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class HousesController {

    @GetMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> getUserHouses(
            @RequestParam int user_id
    ) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        ArrayList<House> housesList = (ArrayList<House>) houseDAO.getHousesList(user_id);

        response.put("houses", housesList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> getUserHouseById(
            @RequestParam int user_id, @PathVariable @RequestParam int house_id
    ) {
        HashMap<String, Object> response = new HashMap<>();

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());

        // either this way {
        House house = houseDAO.getHouseById(user_id, house_id);
        response.put("error", "");
        response.put("code", 0);
        response.put("house_name", house.getName());
        response.put("house_color", house.getColor());
        response.put("house_rooms", house.getRooms());
        // }

        // or this way {
        House house = houseDAO.read(house_id);
        if (house != null) {
            response.put("house_name", house.getName());
            response.put("house_color", house.getColor());
            response.put("house_rooms", house.getRooms());
        } else {
            response.put("error", String.format("house with id %d does not exist", house_id));
            response.put("code", 1);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        // }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/houses")
    public ResponseEntity<HashMap<String, Object>> createHouse (
            @RequestParam String house_name, @RequestParam String house_color
    ) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        House new_house = new House();
        new_house.setName(house_name);
        new_house.setColor(house_color);

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        houseDAO.create(new_house);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/houses/{house_id}")
    public ResponseEntity<HashMap<String, Object>> deleteHouse(
            @PathVariable @RequestParam int house_id
    ) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House del_house = houseDAO.read(house_id);

        if (del_house != null) {
            houseDAO.delete(del_house);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
