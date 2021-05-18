package org.sas.model;

import java.util.Objects;

public class Room {
    private int id;
    private String name;
    private String color;

    // один дом может иметь много комнат -- many-to-one (see Room.hbm.xml)
    private House houseId;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
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

    public House getHouseId() {
        return houseId;
    }

    public void setHouseId(House houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && name.equals(room.name) && color.equals(room.color) && houseId.equals(room.houseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, houseId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", houseId=" + houseId +
                '}';
    }
}
