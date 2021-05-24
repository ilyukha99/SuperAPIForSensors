package org.sas.views;

public class HouseView {
    private String HouseName;
    private String HouseColor;

    public String getHouseName() {
        return HouseName;
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
    }

    public String getHouseColor() {
        return HouseColor;
    }

    public void setHouseColor(String houseColor) {
        HouseColor = houseColor;
    }

    @Override
    public String toString() {
        return "HouseView{" +
                "HouseName='" + HouseName + '\'' +
                ", HouseColor='" + HouseColor + '\'' +
                '}';
    }
}
