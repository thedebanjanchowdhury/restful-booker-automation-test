package com.api.test;

import com.api.base.BookingService;
import com.api.base.GetBookingIDService;
import com.api.models.request.BookingDates;
import com.api.models.request.BookingRequest;
import com.api.models.resoponse.CreateBookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

import java.util.List;


public class GetBookingResponseIdTest {

    private GetBookingIDService booking;

    @Test(
            description = "API-006: Create Booking and Fetch Booking IDs by Name",
            groups = {"user"}
    )
    public void getBookingWithParams() {

        Log.info("API-008: Create Booking and Fetch Booking IDs by Name");

        // Step 1: Create booking (contract insertion)
        BookingService bookingService = new BookingService();

        BookingRequest request = new BookingRequest.Builder()
                .firstname("Sally")
                .lastname("Brown")
                .totalprice(1200)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-02-01", "2025-02-05"))
                .additionalneeds("Breakfast")
                .build();

        Response createResponse = bookingService.createBooking(request);
        createResponse.then().statusCode(200);

        CreateBookingResponse createBookingResponse =
                createResponse.as(CreateBookingResponse.class);

        Assert.assertTrue(createBookingResponse.getBookingid() > 0,
                "Booking ID not created");

        Log.info("Booking created successfully with ID: "
                + createBookingResponse.getBookingid());

        // Step 2: Fetch booking IDs using firstname & lastname
        GetBookingIDService bookingIdService = new GetBookingIDService();

        Response getResponse = bookingIdService.getBoookingIdWithParams(
                "firstname", "Sally",
                "lastname", "Brown"
        );

        getResponse.then().log().all();

        // Step 3: Validate fetch response
        Assert.assertEquals(getResponse.getStatusCode(), 200,
                "Invalid Status Code");

        Log.info("Fetching Booking IDs by Name completed successfully");
    }

    @Test(
            description = "API-007: Returns List of BookingResponse IDs",
            groups = {"user"}
    )
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


}

