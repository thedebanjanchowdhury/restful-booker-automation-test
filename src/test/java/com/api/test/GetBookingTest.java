package com.api.test;

import com.api.base.GetBookingIDService;
import com.api.base.GetBookingService;
import com.api.models.resoponse.GetBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBookingTest {
    @Test
    public void getBookingTest () {
        GetBookingService bookingService = new GetBookingService();
        GetBookingIDService idService = new GetBookingIDService();

        // Store Booking ID from the service
        Response idResponse = idService.getAllBookings();
        int bookingId = idResponse.jsonPath().getInt("bookingid[0]");

        // Get Booking Details
        Response response = bookingService.getBooking(String.valueOf(bookingId));

        // Status code validation
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");

        // JSON Schema Validation
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/get-booking-schema.json"));

        // Deserialization of POJO, and Assertions
        GetBookingResponse bookingResponse = response.as(GetBookingResponse.class);

        Assert.assertNotNull(bookingResponse.getFirstname(), "Firstname is null");
        Assert.assertNotNull(bookingResponse.getLastname(), "Lastname is null");
        Assert.assertNotNull(bookingResponse.getBookingdates(), "Booking Date is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckin(), "Checkin is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckout(), "Checkout is null");

//      Optional Logging
        response.then().log().all();
    }
}
