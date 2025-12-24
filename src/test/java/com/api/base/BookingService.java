package com.api.base;

import com.api.models.request.BookingRequest;
import io.restassured.response.Response;
import utils.Log;

public class BookingService extends BaseService {
    private static final String BASE_URL = "/booking";

    public Response getBooking (String id) {
        Log.info("GetBooking with ID called");
        return getRequest(BASE_URL + "/" + id);
    }

    public Response createBooking (BookingRequest payload) {
        Log.info("Create Booking Request called");
        return postRequest(payload,BASE_URL + "/");
    }

    public Response updateBooking(BookingRequest payload, String token, String id) {
        Log.info("Update Booking Request called");
        return putRequest(payload, BASE_URL + "/" + id, token);
    }

    public Response updateBookingDenial(BookingRequest payload, String token, String id) {
        Log.info("Update Booking Denial Request called");
        return putRequestDenial(payload, BASE_URL + "/" + id, token);
    }

    public Response partialUpdateBooking (BookingRequest payload, String token, String id) {
        Log.info("Partial Update Booking Request called");
        return patchRequest(payload, BASE_URL + "/" + id, token);
    }

    public Response deleteBooking (String id, String token) {
        Log.info("Delete Booking Request called");
        return deleteRequest(BASE_URL + "/" + id, token);
    }

    public Response deleteBookingUnauthorized (String id) {
        Log.info("Delete Booking Unauthorized Request called");
        return deleteRequestUnauthorized(BASE_URL + "/" + id);
    }

}
