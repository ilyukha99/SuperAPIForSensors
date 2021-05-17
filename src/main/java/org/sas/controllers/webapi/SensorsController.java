package org.sas.controllers.webapi;

import org.sas.dao.SensorDataDAO;
import org.sas.model.SensorData;
import org.sas.utils.HibernateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class SensorsController {

    @GetMapping("/sensors/{id}/data")
    public ResponseEntity<HashMap<String, Object>> getDataByDate(@PathVariable int id,
            @RequestParam(required = false) @DateTimeFormat Long start,
            @RequestParam(required = false) Long end) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", "");
        response.put("code", 0);

        SensorDataDAO sensorDataDAO = new SensorDataDAO(HibernateUtils.getSessionFactory());
        ArrayList<SensorData> sensorDataList =
                (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(start, end);
        HashMap<Long, Float> responseDataList = new HashMap<>();
        for (SensorData sensorData: sensorDataList) {
            if (sensorData.getSensor().getId() == id) {
                responseDataList.put(sensorData.getRecordTime().getTime(), sensorData.getValue());
            }
        }
        response.put("data", responseDataList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
