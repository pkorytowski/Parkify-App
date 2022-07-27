package com.parkingsolutions.parkifyapp.data.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ReservationRequest {

    private final HttpRequest httpRequest;

    public ReservationRequest() {
        httpRequest = new HttpRequest();
    }

    public List<ReservationFull> getFullReservations() {
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

    public boolean extendReservationBy15Minutes(String reservationId) {
        String path = "reservation/extend";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", reservationId);
            requestBody.put("time", 15);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return httpRequest.put(path, requestBody);
    }

}
