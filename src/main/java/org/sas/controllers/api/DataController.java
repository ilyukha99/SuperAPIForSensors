package org.sas.controllers.api;

import org.sas.dao.SensorDAO;
import org.sas.dao.SensorDataDAO;
import org.sas.model.SensorData;
import org.sas.utils.HibernateUtils;
import org.sas.views.SensorDataView;
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
    public ResponseEntity<HashMap<String, Object>> addNewData(@RequestBody SensorDataView dataView) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());
        SensorDataDAO sensorDataDAO = new SensorDataDAO(HibernateUtils.getSessionFactory());
        SensorData sensorData = new SensorData(dataView.getSensorId(), sensorDAO.read(dataView.getSensorId()),
                dataView.getValue(), dataView.getRecordTime());
        System.out.println(sensorData);
        sensorDataDAO.create(sensorData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}