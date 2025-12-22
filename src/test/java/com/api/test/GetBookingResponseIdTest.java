package com.api.test;

import com.api.base.GetBookingIDService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class GetBookingResponseIdTest {

    private GetBookingIDService booking;

    @Test(description = "API-007: Returns List of BookingResponse IDs")
    public void getAllBookingIdTest() {
        booking =  new GetBookingIDService();
        Response response = booking.getAllBookings();

        Assert.assertEquals(response.getStatusCode(),200,"Invalid Status Code");
        Assert.assertTrue(response.getContentType().contains(ContentType.JSON.toString()), "Invalid Content Type");

        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertNotNull(bookingIds, "BookingResponse ID List is Null");
        Assert.assertFalse(bookingIds.isEmpty(), "BookingResponse ID List is Empty");
        System.out.println(bookingIds.get(1));

        // Validate Each booking ID
        for (Integer id: bookingIds) {
            Assert.assertNotNull(id, "BookingResponse ID is Null");
            Assert.assertTrue(id > 0, "Invalid BookingResponse ID" + id);
        }
    }

    @Test(description = "API-008: Returns BookingResponse IDs filtered by Name")
    public void getBookingWithParams() {
        booking = new GetBookingIDService();
        Response response = booking.getBoookingIdWithParams(
                "firstname", "sally",
                "lastname", "brown"
        );
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");
        response.then().log().all();
    }
}
