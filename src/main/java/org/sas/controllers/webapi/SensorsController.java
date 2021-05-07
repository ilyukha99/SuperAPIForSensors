package org.sas.controllers.webapi;

import org.sas.model.SensorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;

@Controller
public class SensorsController {

    @GetMapping("/sensors/{id}")
    public ResponseEntity<SensorData> getDataByDate(@PathVariable int id, @RequestParam(required = false)
            Timestamp startDate, @RequestParam(required = false) Timestamp endDate) {

        return new ResponseEntity<SensorData>(new SensorData(), HttpStatus.OK);
    }
}
