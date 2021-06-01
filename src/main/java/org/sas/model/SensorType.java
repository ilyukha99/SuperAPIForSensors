package org.sas.model;

import java.util.Objects;

public class SensorType {
    private int id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "SensorType{id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorType)) return false;
        SensorType sensorType = (SensorType) o;
        return id == sensorType.id && Objects.equals(name, sensorType.name) &&
                Objects.equals(description, sensorType.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
