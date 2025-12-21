package com.api.test;

import com.api.base.GetBookingIDService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class GetBookingIdTest {

    @Test(description = "Test to Check if All Booking IDs are returned or not")
    public void getAllBookingIdTest() {
        GetBookingIDService booking_1 =  new GetBookingIDService();
        Response response = booking_1.getAllBookings();

        // Status Code
        Assert.assertEquals(response.getStatusCode(),200,"Invalid Status Code");

        // Content Type
        Assert.assertTrue(response.getContentType().contains(ContentType.JSON.toString()), "Invalid Content Type");

        // Response Not Empty
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertNotNull(bookingIds, "Booking ID List is Null");
        Assert.assertFalse(bookingIds.isEmpty(), "Booking ID List is Empty");
        System.out.println(bookingIds.get(1));

        // Validate Each booking ID
        for (Integer id: bookingIds) {
            Assert.assertNotNull(id, "Booking ID is Null");
            Assert.assertTrue(id > 0, "Invalid Booking ID" + id);
        }
    }

    @Test(description = "Test to check if all booking IDs corresponding to given parameters return or not.")
    public void getBookingWithParams() {
        GetBookingIDService booking_2 = new GetBookingIDService();
        Response response = booking_2.getBoookingIdWithParams(
                "firstName", "sally",
                "lastName", "Brown"
        );
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid Status Code");
    }

}
