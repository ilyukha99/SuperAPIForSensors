package org.sas.controllers.webapi;

import org.sas.dao.*;
import org.sas.model.House;
import org.sas.model.Room;
import org.sas.model.SensorData;
import org.sas.responses.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SensorsDataController {
    private final SensorDataDAO sensorDataDAO;
    private final HouseDAO houseDAO;
    private final RoomDAO roomDAO;
    private final UserDAO userDAO;

    @Autowired
    public SensorsDataController(@NonNull SensorDataDAO sensorDataDAO, @NonNull HouseDAO houseDAO,
                                 @NonNull RoomDAO roomDAO, @NonNull UserDAO userDAO) {
        this.sensorDataDAO = sensorDataDAO;
        this.houseDAO = houseDAO;
        this.roomDAO = roomDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("houses/{houseId}/rooms/{roomId}/sensors/{id}/data")
    public ResponseEntity<Map<String, Object>> getDataByDate(@PathVariable int id,
                                                             @RequestParam(required = false) Long start,
                                                             @RequestParam(required = false) Long end,
                                                             @PathVariable int houseId, @PathVariable int roomId,
                                                             @RequestHeader("Authorization") String userToken) {
        House house = houseDAO.read(houseId);
        Room room = roomDAO.read(roomId);
        //TODO: maybe need refactor
        if (house != null && room != null) {
            if (userDAO.getUserIdByTokenHeader(userToken) == house.getUserId().getId()) {
                ArrayList<SensorData> sensorDataList =
                        (ArrayList<SensorData>) sensorDataDAO.getSensorDataByDate(start, end);
                HashMap<Long, Float> responseDataList = new HashMap<>();
                for (SensorData sensorData: sensorDataList) {
                    if (sensorData.getSensor().getId() == id) {
                        responseDataList.put(sensorData.getTime().getTime(), sensorData.getValue());
                    }
                }
                return new ResponseEntity<>(new HttpResponse(0 , "")
                        .addResponseParameter("data", responseDataList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new HttpResponse(100, "unauthorized user")
                        .getResponse(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new HttpResponse(1, "Invalid house or room identifier")
                    .getResponse(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
