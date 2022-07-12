package com.parkingsolutions.parkifyapp.data.model;

public class Lane {
    private String name;
    private int size;
    private int availableSpots;

    public Lane(String name, int size) {
        this.name = name;
        this.size = size;
        this.availableSpots = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        availableSpots += size - this.size;
        this.size = size;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }
}
