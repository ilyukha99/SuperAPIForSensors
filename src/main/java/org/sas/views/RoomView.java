package org.sas.views;

public class RoomView {
    private String roomName;
    private String roomColor;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomColor() {
        return roomColor;
    }

    public void setRoomColor(String roomColor) {
        this.roomColor = roomColor;
    }

    @Override
    public String toString() {
        return "RoomView{" +
                "roomName='" + roomName + '\'' +
                ", roomColor='" + roomColor + '\'' +
                '}';
    }
}
