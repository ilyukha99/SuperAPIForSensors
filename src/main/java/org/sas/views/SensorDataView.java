package org.sas.views;

import java.sql.Timestamp;

public class SensorDataView {
    private int sensorId;
    private float value;
    private Timestamp recordTime;

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
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
        return "SensorDataView{sensorId=" + sensorId +
                ", value=" + value +
                ", recordTime=" + recordTime + '}';
    }
}
