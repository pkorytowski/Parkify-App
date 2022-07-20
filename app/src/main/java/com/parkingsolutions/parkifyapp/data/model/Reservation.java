package com.parkingsolutions.parkifyapp.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parkingsolutions.parkifyapp.common.ReservationStatus;

import java.time.LocalDateTime;

public class Reservation {

    private String id;
    private String userId;
    private String parkingId;
    private String laneName;
    private ReservationStatus reservationStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationEnd;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime occupationStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime occupationEnd;

    @JsonCreator
    public Reservation(@JsonProperty("id") String id,
                       @JsonProperty("userId") String userId,
                       @JsonProperty("parkingId") String parkingId,
                       @JsonProperty("laneName") String laneName,
                       @JsonProperty("reservationStatus") ReservationStatus reservationStatus,
                       @JsonProperty("reservationStart") LocalDateTime reservationStart,
                       @JsonProperty("reservationEnd") LocalDateTime reservationEnd,
                       @JsonProperty("occupationStart") LocalDateTime occupationStart,
                       @JsonProperty("occupationEnd") LocalDateTime occupationEnd) {
        this.id = id;
        this.userId = userId;
        this.parkingId = parkingId;
        this.laneName = laneName;
        this.reservationStatus = reservationStatus;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.occupationStart = occupationStart;
        this.occupationEnd = occupationEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public LocalDateTime getOccupationStart() {
        return occupationStart;
    }

    public void setOccupationStart(LocalDateTime occupationStart) {
        this.occupationStart = occupationStart;
    }

    public LocalDateTime getOccupationEnd() {
        return occupationEnd;
    }

    public void setOccupationEnd(LocalDateTime occupationEnd) {
        this.occupationEnd = occupationEnd;
    }

}
