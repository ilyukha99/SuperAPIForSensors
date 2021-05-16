package org.sas.model;

import java.sql.Timestamp;
import java.util.Objects;

public class SensorData {
    private int id;
    private Sensor sensor;
    private float value;
    private Timestamp time;

    public SensorData() {}

    public SensorData(int id, Sensor sensor, float value, Timestamp time) {
        this.id = id;
        this.sensor = sensor;
        this.value = value;
        this.time = time;
    }

    public SensorData() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorData)) return false;
        var sensorData = (SensorData) o;
        return id == sensorData.id && Objects.equals(sensor, sensorData.sensor) && value == sensorData.value
                && Objects.equals(time, sensorData.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensor.getId(), value, time);
    }

    @Override
    public String toString() {
        return "SensorData{id=" + id +
                ", sensor=" + sensor.getId() +
                ", value=" + value +
                ", time=" + time + '}';
    }
}
