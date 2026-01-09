package com.api.test;

import com.api.base.BookingService;
import com.api.base.GenerateTokenService;
import com.api.base.GetBookingIDService;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Log;

public class DeleteBookingTest {

    private String token;
    private int bookingid;
    private BookingService bookingService;

    @BeforeClass
    public void setup () {

        bookingService = new BookingService();

        Log.info("Token Creation Process Started");
        GenerateTokenService generateTokenService = new GenerateTokenService();
        LoginRequest loginRequest = new LoginRequest.Builder().username("admin").password("password123").build();
        LoginResponse loginResponse = generateTokenService.login(loginRequest).as(LoginResponse.class);
        token = loginResponse.getToken();
        Log.info("Token Creation Process Finished; Token: " + token);

        Log.info("ID Fetch Process Started");
        GetBookingIDService getBookingIDService = new GetBookingIDService();
        Response response = getBookingIDService.getAllBookings();
        bookingid = response.jsonPath().getInt("bookingid[0]");
        Log.info("ID Fetch Process Finished; ID: " + bookingid);
    }

    @Test(
            description = "API-013: Delete Post (Authorization)",
            groups = {"user"}
    )
    public void deleteWithAuth() {
        Log.info("Delete Post Process Started");
        Response response = bookingService.deleteBooking(String.valueOf(bookingid),token);
        response.then().statusCode(201).log().all();
        Log.info("Delete Post Process Finished Successfully");
    }

    @Test(
            description = "API-014: Delete Post (Unauthorized)",
            groups = {"user"}

    )
    public void deleteWithUnauthorized() {
        Log.info("Delete Post Process Started");
        Response response = bookingService.deleteBookingUnauthorized(String.valueOf(bookingid));
        response.then().statusCode(403).log().all();
        Log.info("Delete Post Process Finished Successfully");
    }

}
