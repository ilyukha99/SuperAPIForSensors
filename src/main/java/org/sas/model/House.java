package org.sas.model;

import java.util.Objects;


public class House {
    private int id;
    private String name;
    private String color;

    // один юзер может иметь много домов -- many-to-one (see House.hbm.xml)
    private User userId;

    public User getUserId() { return userId; }

    public void setUserId(User userId) { this.userId = userId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return id == house.id && name.equals(house.name) && color.equals(house.color) && Objects.equals(userId, house.userId);
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
