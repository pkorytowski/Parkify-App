package com.parkingsolutions.parkifyapp.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationFull {
    Reservation reservation;
    Parking parking;

    @JsonCreator
    public ReservationFull(@JsonProperty("reservation") Reservation reservation, @JsonProperty("parking") Parking parking) {
        this.reservation = reservation;
        this.parking = parking;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
