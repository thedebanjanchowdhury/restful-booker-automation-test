package com.api.test;

import com.api.base.GetBookingIDService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

import java.util.List;


public class GetBookingResponseIdTest {

    private GetBookingIDService booking;

    @Test(description = "API-007: Returns List of BookingResponse IDs")
    public void getAllBookingIdTest() {
        Log.info("API-007: Get All Booking Response IDs");

        Log.info("Getting All BookingResponse IDs");
        booking =  new GetBookingIDService();
        Response response = booking.getAllBookings();

        Assert.assertEquals(response.getStatusCode(),200,"Invalid Status Code");
        Assert.assertTrue(response.getContentType().contains(ContentType.JSON.toString()), "Invalid Content Type");

        Log.info("Mapping all BookingResponse IDs to Booking Response");
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertNotNull(bookingIds, "BookingResponse ID List is Null");
        Assert.assertFalse(bookingIds.isEmpty(), "BookingResponse ID List is Empty");
        System.out.println(bookingIds.get(1));

        // Validate Each booking ID
        Log.info("Assertion for All Returned Booking IDs");
        for (Integer id: bookingIds) {
            Assert.assertNotNull(id, "BookingResponse ID is Null");
            Assert.assertTrue(id > 0, "Invalid BookingResponse ID" + id);
        }
        Log.info("GetAllBookingIDTest Completed Successfully");
    }

    @Test(description = "API-008: Returns BookingResponse IDs filtered by Name")
    public void getBookingWithParams() {

        Log.info("API-008: Get Booking With Params");

        Log.info("Getting BookingResponse IDs filtered by Name Test Started");
        booking = new GetBookingIDService();
        Response response = booking.getBoookingIdWithParams(
                "firstname", "sally",
                "lastname", "brown"
        );
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");
        Log.info("Getting BookingResponse IDs filtered by Name Test Completed");
    }
}
