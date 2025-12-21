package com.api.base;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class GetBookingIDService extends BaseService {

    private static final String BASE_PATH = "/booking";

    public Response getAllBookings () {
     return getRequest(BASE_PATH);
    }

    /*
    * Returns all ids
    *  */
    public Response getBookingId (String payload) {
        return getRequest(payload, BASE_PATH);
    }

    /*
    * Payload with query parameters
    * */
    public Response getBoookingIdWithParams (Object... params) {
        Map<String, Object> queryParams = buildQueryParams(params);
        return  getRequestWithParams(BASE_PATH, queryParams);
    }

    private Map<String, Object> buildQueryParams (Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Query params must be in key:value pairs");
        }

        Map<String, Object> queryParams = new HashMap<>();
        for (int i=0; i<params.length; i += 2) {
            queryParams.put(String.valueOf(params[i]), String.valueOf(params[i+1]));
        }

        return queryParams;
    }
}
