package com.api.test;

import com.api.base.BookingService;
import com.api.base.GenerateTokenService;
import com.api.base.GetBookingIDService;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteBookingTest {

    private String token;
    private int bookingid;
    private BookingService bookingService;

    @BeforeMethod
    public void setup () {

        bookingService = new BookingService();

        GenerateTokenService generateTokenService = new GenerateTokenService();
        LoginRequest loginRequest = new LoginRequest.Builder().username("admin").password("password123").build();
        LoginResponse loginResponse = generateTokenService.login(loginRequest).as(LoginResponse.class);
        token = loginResponse.getToken();

        GetBookingIDService getBookingIDService = new GetBookingIDService();
        Response response = getBookingIDService.getAllBookings();
        bookingid = response.jsonPath().getInt("bookingid[0]");
    }

    @Test(description = "API-010: Delete Post (Authorization)")
    public void deleteWithAuth() {
        Response response = bookingService.deleteBooking(String.valueOf(bookingid),token);
        response.then().statusCode(201).log().all();
    }

    @Test(description = "API-011: Delete Post (Unauthorized)")
    public void deleteWithUnauthorized() {
        Response response = bookingService.deleteBookingUnauthorized(String.valueOf(bookingid));
        response.then().statusCode(403);
    }

}
