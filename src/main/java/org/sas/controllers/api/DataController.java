package org.sas.controllers.api;

import org.sas.dao.SensorDAO;
import org.sas.dao.SensorDataDAO;
import org.sas.model.Sensor;
import org.sas.model.SensorData;
import org.sas.exceptions.handlers.CustomExceptionHandler;
import org.sas.utils.HibernateUtils;
import org.sas.utils.HttpResponse;
import org.sas.views.SensorDataView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CustomExceptionHandler
public class DataController {
    @PostMapping("/api/data")
    public ResponseEntity<HttpResponse> addNewData(@RequestBody SensorDataView dataView) {
        if (dataView.getRecordTime() == null) {
            return new ResponseEntity<>(new HttpResponse(1,
                    "incorrect timestamp given, json pattern: 'yyyy-mm-ddThh:mm:ssZ"), HttpStatus.BAD_REQUEST);
        }
        SensorDAO sensorDAO = new SensorDAO(HibernateUtils.getSessionFactory());
        SensorDataDAO sensorDataDAO = new SensorDataDAO(HibernateUtils.getSessionFactory());
        Sensor sensor = sensorDAO.read(dataView.getSensorId());
        if (sensor == null) {
            return new ResponseEntity<>(new HttpResponse(2, "incorrect sensor id given"),
                    HttpStatus.BAD_REQUEST);
        }
        SensorData sensorData = new SensorData(0, sensor, dataView.getValue(), dataView.getRecordTime());

        sensorDataDAO.create(sensorData);
        return new ResponseEntity<>(new HttpResponse(0, ""), HttpStatus.OK);
    }

    @GetMapping("/api/data")
    public ResponseEntity<HttpResponse> greet() {
        return new ResponseEntity<>(new HttpResponse(0, "hello"), HttpStatus.OK);
    }
}