package com.parkingsolutions.parkifyapp.data.request;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingsolutions.parkifyapp.MainActivity;
import com.parkingsolutions.parkifyapp.data.Result;
import com.parkingsolutions.parkifyapp.data.model.AuthorizedUser;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;

import java.util.List;

public class ReservationRequest {

    private final HttpRequest httpRequest;

    public ReservationRequest() {
        httpRequest = new HttpRequest();
    }

    public List<ReservationFull> getFullReservations() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        String path = "reservation/active";

        String resp = httpRequest.get(path);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<ReservationFull> reservationFullList;
        try {
            reservationFullList = mapper.readValue(resp, new TypeReference<List<ReservationFull>>(){});
            return reservationFullList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
