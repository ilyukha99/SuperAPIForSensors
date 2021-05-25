package org.sas.controllers.api;

import org.sas.dao.SensorDAO;
import org.sas.dao.SensorDataDAO;
import org.sas.dao.UserDAO;
import org.sas.model.Sensor;
import org.sas.model.SensorData;
import org.sas.exceptions.handlers.CustomExceptionHandler;
import org.sas.responses.HttpResponse;
import org.sas.security.jwt.JwtProvider;
import org.sas.views.SensorDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CustomExceptionHandler
public class DataController {
    private final SensorDAO sensorDAO;
    private final SensorDataDAO sensorDataDAO;
    private final UserDAO userDAO;
    private final JwtProvider jwtProvider;

    @Autowired
    public DataController(@NonNull SensorDAO sensorDAO, @NonNull SensorDataDAO sensorDataDAO,
                          @NonNull UserDAO userDao, @NonNull JwtProvider jwtProvider) {
        this.sensorDAO = sensorDAO;
        this.sensorDataDAO = sensorDataDAO;
        this.userDAO = userDao;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/api/data")
    public ResponseEntity<Map<String, Object>> addNewData(@RequestBody SensorDataView dataView) {
        String sensorToken = dataView.getSensorToken();
        if (dataView.getRecordTime() == null) {
            return new ResponseEntity<>(new HttpResponse(1,
                    "incorrect timestamp given, json pattern: 'yyyy-mm-ddThh:mm:ssZ").getResponse(),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDAO.sensorTokenExists(sensorToken)) {
            return new ResponseEntity<>(new HttpResponse(2, "incorrect sensor token given")
                    .getResponse(), HttpStatus.BAD_REQUEST);
        }

        if (!sensorDAO.getSensorOwnerLogin(dataView.getSensorId())
                .equals(jwtProvider.getLoginFromToken(sensorToken))) {
            return new ResponseEntity<>(new HttpResponse(4, "incorrect sensor owner").getResponse(),
                    HttpStatus.BAD_REQUEST);
        }

        Sensor sensor = sensorDAO.read(dataView.getSensorId());
        if (sensor == null) {
            return new ResponseEntity<>(new HttpResponse(3, "incorrect sensor id given")
                    .getResponse(), HttpStatus.BAD_REQUEST);
        }
        SensorData sensorData = new SensorData(sensor, dataView.getValue(), dataView.getRecordTime());

        sensorDataDAO.create(sensorData);
        return new ResponseEntity<>(new HttpResponse(0, "").getResponse(), HttpStatus.OK);
    }

    @GetMapping("/api/data")
    public ResponseEntity<Map<String, Object>> greet() {
        return new ResponseEntity<>(new HttpResponse(0, "we greet you").getResponse(), HttpStatus.OK);
    }
}