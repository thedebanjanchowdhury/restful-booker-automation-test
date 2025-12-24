package com.api.base;

import io.restassured.response.Response;
import utils.Log;

import java.util.HashMap;
import java.util.Map;

public class GetBookingIDService extends BaseService {

    private static final String BASE_PATH = "/booking";

    public Response getAllBookings () {
        Log.info("Get All Bookings Request called");
        return getRequest(BASE_PATH);
    }

    /*
    * Returns all ids
    *  */
    public Response getBookingId (String payload) {
        Log.info("Get Booking Id Request called");
        return getRequest(payload, BASE_PATH);
    }

    /*
    * Payload with query parameters
    * */
    public Response getBoookingIdWithParams (Object... params) {
        Log.info("Get Booking Id With Params Request called");
        Map<String, Object> queryParams = buildQueryParams(params);
        return  getRequestWithParams(BASE_PATH, queryParams);
    }

    private Map<String, Object> buildQueryParams (Object... params) {
        Log.info("Build Query Params Request called");
        if (params.length % 2 != 0) {
            Log.warn(params.length + " is invalid for query params");
            throw new IllegalArgumentException("Query params must be in key:value pairs");
        }

        Map<String, Object> queryParams = new HashMap<>();
        for (int i=0; i<params.length; i += 2) {
            queryParams.put(String.valueOf(params[i]), String.valueOf(params[i+1]));
        }

        return queryParams;
    }
}
