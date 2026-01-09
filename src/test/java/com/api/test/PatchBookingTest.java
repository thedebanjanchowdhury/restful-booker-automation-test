package com.api.test;

import com.api.base.BookingService;
import com.api.base.GenerateTokenService;
import com.api.base.GetBookingIDService;
import com.api.models.request.BookingDates;
import com.api.models.request.BookingRequest;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Log;

import static org.hamcrest.Matchers.equalTo;

public class PatchBookingTest {

    private BookingService bookingService;
    private Response response;
    private int bookingId;
    private String token;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        Log.info("Setup Method");
        bookingService = new BookingService();

        Log.info("Login Request Created");
        LoginRequest loginRequest = new LoginRequest.Builder()
                .username("admin").password("password123").build();

        Log.info("Token Request Started");
        GenerateTokenService generateTokenService = new GenerateTokenService();
        Response loginResp = generateTokenService.login(loginRequest);
        if (loginResp.getStatusCode() != 200) {
            String errorMsg = "Login failed in setup. Status: " + loginResp.getStatusLine() + ", Body: " + loginResp.asString();
            Log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        LoginResponse loginResponse = loginResp.as(LoginResponse.class);
        token = loginResponse.getToken();
        Log.info("Token Created: " + token);

        Log.info("Creating a new booking for Patch Test");
        BookingRequest bookingRequest = new BookingRequest.Builder()
                .firstname("PatchTest")
                .lastname("User")
                .totalprice(500)
                .depositpaid(true)
                .bookingdates(new BookingDates("2024-05-01", "2024-05-10"))
                .additionalneeds("Lunch")
                .build();
        Response createResponse = bookingService.createBooking(bookingRequest);
        if (createResponse.getStatusCode() == 418) {
            String warningMsg = "API Blocked (418 I'm a teapot). Skipping setup.";
            Log.warn(warningMsg);
            throw new org.testng.SkipException(warningMsg);
        }
        if (createResponse.getStatusCode() != 200) {
            String errorMsg = "Failed to create booking in setup. Status: " + createResponse.getStatusLine() + ", Body: " + createResponse.asString();
            Log.error(errorMsg);
            // Don't throw exception, just proceed
        } else {
            bookingId = createResponse.jsonPath().getInt("bookingid");
            Log.info("Created booking for patch test with ID: " + bookingId);
        }
    }

    @Test(
            description = "API-012: Partial Update Using Patch",
            groups = {"user"}
    )
    public void testPatchBooking () {

        Log.info("API-012: Partial Update Using Patch");

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
