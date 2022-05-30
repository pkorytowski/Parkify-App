package com.parkingsolutions.parkifyapp.common;

import org.json.JSONObject;

public class JSONMapper {

    public static JSONObject convertToJSON(String text) {
        try {

            return new JSONObject(text);

        } catch (Throwable t) {
            return new JSONObject();
        }
    }
}
