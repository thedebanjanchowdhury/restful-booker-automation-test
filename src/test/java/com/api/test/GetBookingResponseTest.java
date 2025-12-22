package com.api.test;

import com.api.base.GetBookingIDService;
import com.api.base.BookingService;
import com.api.models.resoponse.GetBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBookingResponseTest {

    private BookingService bookingService;
    private int bookingid;

    @BeforeMethod
    public void setup() {
        bookingService = new BookingService();
        GetBookingIDService getBookingIDService = new GetBookingIDService();

        Response idResponse = getBookingIDService.getAllBookings();
        bookingid = idResponse.jsonPath().getInt("bookingid[0]");
    }

    @Test(description = "API-005: Get BookingResponse Details")
    public void getBookingTest () {
        Response response = bookingService.getBooking(String.valueOf(bookingid));
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");

        // JSON Schema Validation
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/get-booking-schema.json"));

        // Deserialization of POJO, and Assertions
        GetBookingResponse bookingResponse = response.as(GetBookingResponse.class);
        Assert.assertNotNull(bookingResponse.getFirstname(), "Firstname is null");
        Assert.assertNotNull(bookingResponse.getLastname(), "Lastname is null");
        Assert.assertNotNull(bookingResponse.getBookingdates(), "BookingResponse Date is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckin(), "Checkin is null");
        Assert.assertNotNull(bookingResponse.getBookingdates().getCheckout(), "Checkout is null");
    }

    @Test(description = "API-006: GetBooking with Invalid ID")
    public void getBookingWithInvalidIdTest () {
        Response response = bookingService.getBooking(String.valueOf(999999999));
        Assert.assertEquals(response.getStatusCode(), 404, "Invalid Status Code");
        response.then().log().all();
    }
}
