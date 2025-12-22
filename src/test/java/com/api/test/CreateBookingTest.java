package com.api.test;

import com.api.base.BookingService;
import com.api.models.request.BookingDates;
import com.api.models.request.CreateBookingRequest;
import com.api.models.resoponse.CreateBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingTest {

    @Test(description = "Test script to test booking creation")
    public void createBookingTest()  {
        BookingService bookingService = new BookingService();

        //Builder Class
        CreateBookingRequest request = new CreateBookingRequest.Builder().firstname("Debanjan")
                .lastname("Chowdhury")
                .totalprice(1000)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-01-01", "2025-01-02"))
                .additionalneeds("Breakfast")
                .build();

        // Response, schema validation and deserialization
        Response response = bookingService.createBooking(request);
        response.then().statusCode(200).body(matchesJsonSchemaInClasspath("schemas/create-booking-schema.json"));
        CreateBookingResponse res = response.as(CreateBookingResponse.class);

        // Assertion of Different fields
        Assert.assertTrue(res.getBookingid() > 0);
        Assert.assertEquals(res.getBooking().getFirstname(), "Debanjan");
        Assert.assertEquals(res.getBooking().getLastname(), "Chowdhury");
        Assert.assertEquals(res.getBooking().getTotalprice(), 1000);
        Assert.assertTrue(res.getBooking().isDepositpaid());
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckin(), "2025-01-01");
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckout(), "2025-01-02");
        Assert.assertEquals(res.getBooking().getAdditionalneeds(), "Breakfast");
    }

}
