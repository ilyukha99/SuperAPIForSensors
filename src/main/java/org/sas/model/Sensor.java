package org.sas.model;

import java.util.Objects;

public class Sensor {
    private int id;
    private String name;
    private SensorType type;
    private User user;

    // одна комната может иметь много сенсоров -- many-to-one (see Sensor.hbm.xml)
    private Room roomId;

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Sensor{id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type.getId() +
                ", user=" + user.getId() +
                ", room="+ roomId.getId() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        Sensor sensor = (Sensor) o;
        return id == sensor.id && Objects.equals(name, sensor.name) && type.getId() == sensor.type.getId()
                && user.getId() == sensor.user.getId() && roomId.getId() == sensor.roomId.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type.getName(), user.getLogin(), roomId.getId());
    }
}
