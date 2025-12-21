package com.api.test;

import com.api.base.GetBookingIDService;
import com.api.base.GetBookingService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetBookingTest {
    @Test
    public void getBookingTest () {
        GetBookingService bookingService = new GetBookingService();
        GetBookingIDService idService = new GetBookingIDService();

        // Store Booking ID from the service
        Response idResponse = idService.getAllBookings();
        int bookingId = idResponse.jsonPath().getInt("bookingid[0]");

        Response bookingResponse = bookingService.getBooking(String.valueOf(bookingId));


        Assert.assertEquals(bookingResponse.getStatusCode(), 200, "Invalid Status Code");
        bookingResponse.then().log().all();
    }
}
