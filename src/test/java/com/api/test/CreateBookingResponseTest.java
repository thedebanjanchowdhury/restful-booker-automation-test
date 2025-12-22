package com.api.test;

import com.api.base.BookingService;
import com.api.models.request.BookingDates;
import com.api.models.request.BookingRequest;
import com.api.models.resoponse.CreateBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingResponseTest {
    private BookingService bookingService;
    private BookingRequest request;

    @BeforeMethod
    public void setup() {
        bookingService = new BookingService();
        request = new BookingRequest.Builder().firstname("Debanjan")
                .lastname("Chowdhury")
                .totalprice(1000)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-01-01", "2025-01-02"))
                .additionalneeds("Breakfast")
                .build();

    }

    @Test(description = "API-004: Create BookingResponse Test")
    public void createBookingTest()  {

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
