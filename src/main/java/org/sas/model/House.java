package org.sas.model;

import java.util.Objects;

public class House {
    private int id;
    private String name;

    public House(int id, String name, String color, int user_id) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.user_id = user_id;
    }

    private String color;
    private int user_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "House{id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", user_id=" + user_id + '}';
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
        return id == house.id && user_id == house.user_id && Objects.equals(name, house.name)
                && Objects.equals(color, house.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, user_id);
    }
}
