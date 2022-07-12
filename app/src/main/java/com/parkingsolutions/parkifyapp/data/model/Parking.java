package com.parkingsolutions.parkifyapp.data.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parking {
    private String id;

    private String ownerId;
    private String name;
    private String city;
    private String street;
    private String number;
    private String postalcode;
    private String country;
    private int size;
    private int availableSpots;
    private List<Lane> lanes;

    public Parking() {}


    public Parking(String ownerId,
                   String name,
                   String city,
                   String street,
                   String number,
                   String postalcode,
                   String country,
                   List<Lane> lanes){
        this.ownerId = ownerId;
        this.name = name;
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalcode = postalcode;
        this.country = country;
        this.lanes = lanes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
