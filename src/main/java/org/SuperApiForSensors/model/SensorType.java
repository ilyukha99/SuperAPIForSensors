package org.SuperApiForSensors.model;

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
}
