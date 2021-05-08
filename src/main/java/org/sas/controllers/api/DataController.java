package org.sas.controllers.api;

import org.sas.dao.SensorDataDAO;
import org.sas.model.SensorData;
import org.sas.utils.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/data")
public class DataController {
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> addNewData(@RequestBody SensorData data) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        SensorDataDAO sensorDataDAO = new SensorDataDAO(HibernateUtils.getSessionFactory());
        sensorDataDAO.create(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}