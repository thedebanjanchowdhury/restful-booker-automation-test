package com.api.base;

import io.restassured.response.Response;

public class GetBookingService extends BaseService {
    private static final String BASE_URL = "/booking";

    public Response getBooking (String id) {
        return getRequest(BASE_URL + "/" + id);
    }
}
