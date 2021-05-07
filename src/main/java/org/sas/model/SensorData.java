package org.sas.model;

import java.util.Date;
import java.util.Objects;

public class SensorData {
    private int id;
    private int sensorId;
    private float value;
    private Date recordDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorData)) return false;
        var sensorData = (SensorData) o;
        return id == sensorData.id && sensorId == sensorData.sensorId && value == sensorData.value
                && Objects.equals(recordDate, sensorData.recordDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sensorId, value, recordDate);
    }

    @Override
    public String toString() {
        return "SensorData{id=" + id +
                ", sensorId=" + sensorId +
                ", value=" + value +
                ", recordDate=" + recordDate + '}';
    }
}
