package org.sas.views;

import java.sql.Timestamp;

public class SensorDataView {
    private int id;
    private int sensorId;
    private float value;
    private Timestamp recordTime;

    public SensorDataView(int id, int sensorId, float value, Timestamp recordTime) {
        this.id = id;
        this.sensorId = sensorId;
        this.value = value;
        this.recordTime = recordTime;
    }

    public SensorDataView() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public int getId() {
        return id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public float getValue() {
        return value;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    @Override
    public String toString() {
        return "SensorDataView{" +
                "id=" + id +
                ", sensorId=" + sensorId +
                ", value=" + value +
                ", recordTime=" + recordTime +
                '}';
    }
}
