package com.parkingsolutions.parkifyapp.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Lane {
    private String name;
    private int size;
    private int availableSpots;

    @JsonCreator
    public Lane(@JsonProperty("name") String name,
                @JsonProperty("size") int size,
                @JsonProperty("availableSpots") int availableSpots) {
        this.name = name;
        this.size = size;
        this.availableSpots = availableSpots;
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
