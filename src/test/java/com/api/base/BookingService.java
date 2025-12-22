package com.api.base;

import com.api.models.request.BookingRequest;
import io.restassured.response.Response;

public class BookingService extends BaseService {
    private static final String BASE_URL = "/booking";

    public Response getBooking (String id) {
        return getRequest(BASE_URL + "/" + id);
    }

    public Response createBooking (BookingRequest payload) {
        return postRequest(payload,BASE_URL + "/");
    }

    public Response updateBooking(BookingRequest payload, String token, String id) {
        return putRequest(payload, BASE_URL + "/" + id, token);
    }

    public Response updateBookingDenial(BookingRequest payload, String token, String id) {
        return putRequestDenial(payload, BASE_URL + "/" + id, token);
    }

}
