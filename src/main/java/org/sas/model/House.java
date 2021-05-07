package org.sas.model;

import java.util.Objects;

public class House {
    private int id;
    private String name;
    private String color;
    private int userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return id == house.id && userId == house.userId && Objects.equals(name, house.name)
                && Objects.equals(color, house.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, userId);
    }

    @Override
    public String toString() {
        return "House{id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", userId=" + userId + '}';
    }
}
