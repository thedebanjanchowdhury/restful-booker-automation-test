package com.api.base;

import com.api.models.request.CreateBookingRequest;
import io.restassured.response.Response;

public class BookingService extends BaseService {
    private static final String BASE_URL = "/booking";

    public Response getBooking (String id) {
        return getRequest(BASE_URL + "/" + id);
    }

    public Response createBooking (CreateBookingRequest payload) {
        return postRequest(payload,BASE_URL + "/");
    }

}
