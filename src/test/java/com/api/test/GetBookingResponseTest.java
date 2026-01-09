package com.api.test;

import com.api.base.GetBookingIDService;
import com.api.base.BookingService;
import com.api.models.resoponse.GetBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Log;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBookingResponseTest {

    private BookingService bookingService;
    private int bookingid;

    @BeforeMethod
    public void setup() {
        Log.info("Setup Method, Extracting Booking ID");
        bookingService = new BookingService();
        GetBookingIDService getBookingIDService = new GetBookingIDService();

        Response idResponse = getBookingIDService.getAllBookings();
        bookingid = idResponse.jsonPath().getInt("bookingid[0]");
    }

    @Test(
            description = "API-008: Get BookingResponse Details",
            groups = {"user"}
    )
    public void getBookingTest () {
        Log.info("API-005: Get BookingResponse Details");

        Log.info("Get Booking Test Started");
        Response response = bookingService.getBooking(String.valueOf(bookingid));
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");

        // JSON Schema Validation
        Log.info("JSON Schema Validation Started");
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/get-booking-schema.json"));

        // Deserialization of POJO, and Assertions
        GetBookingResponse bookingResponse = response.as(GetBookingResponse.class);
        Assert.assertNotNull(bookingResponse.getFirstname(), "Firstname is null");
        Assert.assertNotNull(bookingResponse.getLastname(), "Lastname is null");
        Assert.assertNotNull(bookingResponse.getBookingdates(), "BookingResponse Date is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckin(), "Checkin is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckout(), "Checkout is null");

        Log.info("Get Booking Test Completed Successfully");
    }

    @Test(
            description = "API-009: GetBooking with Invalid ID",
            groups = {"user"}
    )
    public void getBookingWithInvalidIdTest () {
        Log.info("API-006: Get Booking with Invalid ID");

        Log.info("Get Booking Test Started");
        Response response = bookingService.getBooking(String.valueOf(999999999));
        Assert.assertEquals(response.getStatusCode(), 404, "Invalid Status Code");
        Log.info("Invalid Booking Test Completed Successfully");
    }
}
