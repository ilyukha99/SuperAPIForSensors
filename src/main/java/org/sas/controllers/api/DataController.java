package org.sas.controllers.api;

import org.sas.dao.SensorDataDAO;
import org.sas.model.SensorData;
import org.sas.utils.HibernateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    @PostMapping
    public void addNewData(@RequestBody SensorData data) {
        SensorDataDAO sensorDataDAO = new SensorDataDAO(HibernateUtils.getSessionFactory());
        sensorDataDAO.create(data);
    }
}