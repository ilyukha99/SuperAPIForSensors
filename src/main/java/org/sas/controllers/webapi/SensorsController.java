package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.model.House;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SensorsController {
    private final HouseDAO houseDAO;

    public SensorsController(@NonNull HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @GetMapping("/houses/{houseId}/sensors")
    public ResponseEntity<HashMap<String, Object>> getSensors(@PathVariable int houseId) {
        HashMap<String, Object> response = new HashMap<>();

        House house = houseDAO.read(houseId);
        if (house != null) {
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
