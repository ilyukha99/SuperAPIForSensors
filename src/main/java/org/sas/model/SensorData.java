package org.sas.model;

import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Objects;

public class SensorData {
    private int id;
    private Sensor sensor;
    private float value;
    private Timestamp recordTime;

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

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordDate) {
        this.recordTime = recordDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorData)) return false;
        var sensorData = (SensorData) o;
        Hibernate.initialize(sensor);
        return id == sensorData.id && sensor == sensorData.sensor && value == sensorData.value
                && Objects.equals(recordTime, sensorData.recordTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensor.getId(), value, recordTime);
    }

    @Override
    public String toString() {
        return "SensorData{id=" + id +
                ", sensorId=" + sensor.getId() +
                ", value=" + value +
                ", recordDate=" + recordTime + '}';
    }
}
