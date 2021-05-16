package org.sas.controllers.webapi;

import org.sas.dao.HouseDAO;
import org.sas.model.House;
import org.sas.utils.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SensorsController {

    @GetMapping("/houses/{houseId}/sensors")
    public ResponseEntity<HashMap<String, Object>> getSensors(@PathVariable int houseId) {
        HashMap<String, Object> response = new HashMap<>();

        HouseDAO houseDAO = new HouseDAO(HibernateUtils.getSessionFactory());
        House house = houseDAO.read(houseId);
        if (house != null) {
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
