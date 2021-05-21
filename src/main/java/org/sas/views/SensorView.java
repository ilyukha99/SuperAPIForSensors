package org.sas.views;

public class SensorView {
    private String name;
    private int typeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "SensorView{" +
                "name=" + name +
                ", sensorTypeId=" + typeId +
                '}';
    }
}
