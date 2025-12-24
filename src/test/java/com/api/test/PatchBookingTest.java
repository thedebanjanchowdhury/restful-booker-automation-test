package com.api.test;

import com.api.base.BookingService;
import com.api.base.GenerateTokenService;
import com.api.base.GetBookingIDService;
import com.api.models.request.BookingDates;
import com.api.models.request.BookingRequest;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Log;

import static org.hamcrest.Matchers.equalTo;

public class PatchBookingTest {

    private BookingService bookingService;
    private Response response;
    private int bookingId;
    private String token;

    @BeforeMethod
    public void setup() {
        Log.info("Setup Method");
        bookingService = new BookingService();

        Log.info("Login Request Created");
        LoginRequest loginRequest = new LoginRequest.Builder()
                .username("admin").password("password123").build();

        Log.info("Token Request Started");
        GenerateTokenService generateTokenService = new GenerateTokenService();
        LoginResponse loginResponse = generateTokenService.login(loginRequest).as(LoginResponse.class);
        token = loginResponse.getToken();
        Log.info("Token Created: " + token);

        Log.info("Booking Id Creation Started");
        GetBookingIDService  getBookingIDService = new GetBookingIDService();
        response = getBookingIDService.getAllBookings();
        bookingId = response.jsonPath().getInt("bookingid[0]");
        Log.info("Booking Id Created: " + bookingId);
    }

    @Test(description = "API-009: Partial Update Using Patch")
    public void testPatchBooking () {

        Log.info("API-009: Partial Update Using Patch");

        Log.info("Partial Update Request Object Initialized");
        BookingRequest bookingRequest = new BookingRequest.Builder()
                .firstname("UserJimmy")
                .lastname("UserBrown")
                .depositpaid(false)
                .totalprice(8000)
                .bookingdates(
                        new BookingDates("2024-01-01", "2024-01-10")
                )
                .build();
        Log.info("Partial Update Request Object Created");

        Log.info("Response Captured and Response Assertion Started");
        response = bookingService.partialUpdateBooking(bookingRequest,token,String.valueOf(bookingId));
        response.then().assertThat().statusCode(200)
                .body("firstname", equalTo("UserJimmy"))
                .body("lastname", equalTo("UserBrown"))
                .body("depositpaid", equalTo(false))
                .body("totalprice", equalTo(8000));
        Log.info("Partial Update Request Tested Successfully");
    }
}
