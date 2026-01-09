package com.api.test;

import com.api.base.BookingService;
import com.api.models.request.BookingDates;
import com.api.models.request.BookingRequest;
import com.api.models.resoponse.CreateBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Log;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingResponseTest {

    private BookingService bookingService;
    private BookingRequest request1,request2;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        Log.info("Initializing BookingService and BookingRequest");

        bookingService = new BookingService();
        request1 = new BookingRequest.Builder()
                .firstname("Debanjan")
                .lastname("Chowdhury")
                .totalprice(1000)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-01-01", "2025-01-02"))
                .additionalneeds("Breakfast")
                .build();

        request2 = new BookingRequest.Builder()
                .firstname("Debanjan")
                .totalprice(500)
                .depositpaid(true)
                .additionalneeds("Breakfast")
                .build();

        Log.debug("BookingRequest built successfully");
    }

    @Test(
            description = "API-004: Create BookingResponse Test",
            groups = {"user"}
    )
    public void createBookingTest() {

        Log.info("Sending Create Booking request");
        Response response = bookingService.createBooking(request1);
        response.then().statusCode(200);


        Log.info("Validating response status and schema");
        if (response.getStatusCode() != 200) {
            String errorMsg = "Failed booking. Status: " + response.getStatusLine() + ", Body: " + response.asString();
            Log.error(errorMsg);
        }
        response.then().statusCode(200);

        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/create-booking-schema.json"));

        CreateBookingResponse res = response.as(CreateBookingResponse.class);
        Log.info("Response deserialized successfully. Booking ID: " + res.getBookingid());

        Assert.assertTrue(res.getBookingid() > 0);
        Assert.assertEquals(res.getBooking().getFirstname(), "Debanjan");
        Assert.assertEquals(res.getBooking().getLastname(), "Chowdhury");
        Assert.assertEquals(res.getBooking().getTotalprice(), 1000);
        Assert.assertTrue(res.getBooking().isDepositpaid());
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckin(), "2025-01-01");
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckout(), "2025-01-02");
        Assert.assertEquals(res.getBooking().getAdditionalneeds(), "Breakfast");

        Log.info("Create Booking test passed successfully");
    }

    @Test(
            description = "API-005: Booking Creation with Partial Fields",
            groups = {"user"}
    )
    public void createBookingWithPartialFieldsTest() {
        Log.info("API-005: Creating Booking with Partial Fields");

        Log.info("Sending Create Booking request");
        Response response = bookingService.createBooking(request2);
        response.then().statusCode(500);
        String error = response.asString();
        Assert.assertEquals(error, "Internal Server Error");
    }

}
