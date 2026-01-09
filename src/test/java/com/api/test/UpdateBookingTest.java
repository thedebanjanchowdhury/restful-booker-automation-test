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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateBookingTest {

    private BookingService bookingService;
    private String token;
    private int bookingId;

    @BeforeClass
    public void setup() {

        Log.info("Generating auth token");

        LoginRequest request = new LoginRequest.Builder()
                .username("admin")
                .password("password123")
                .build();

        GenerateTokenService generateTokenService = new GenerateTokenService();
        LoginResponse loginResponse =
                generateTokenService.login(request).as(LoginResponse.class);

        token = loginResponse.getToken();
        Log.debug("Token generated successfully");

        Log.info("Creating a new booking for test");
        bookingService = new BookingService();
        BookingRequest bookingRequest = new BookingRequest.Builder()
                .firstname("Test")
                .lastname("User")
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(new BookingDates("2024-01-01", "2024-01-02"))
                .additionalneeds("None")
                .build();
        
        Response createResponse = bookingService.createBooking(bookingRequest);
        bookingId = createResponse.jsonPath().getInt("bookingid");
        Log.info("Created booking with ID: " + bookingId);
    }

    @Test(
            description = "API-010: Update Booking Check",
            groups = {"user"}
    )
    public void updateBookingTest() {

        Log.info("API-010: Starting Update Booking test");

        bookingService = new BookingService();

        BookingRequest bookingRequest = new BookingRequest.Builder()
                .firstname("UpdatedJim")
                .lastname("UpdatedBrown")
                .totalprice(1000)
                .depositpaid(false)
                .bookingdates(new BookingDates("2025-12-25", "2025-12-30"))
                .additionalneeds("Dinner")
                .build();

        Log.info("Sending update request for bookingId: " + bookingId);

        Response updateResponse =
                bookingService.updateBooking(bookingRequest, token, String.valueOf(bookingId));

        updateResponse.then()
                .statusCode(200)
                .body("firstname", equalTo("UpdatedJim"))
                .body("lastname", equalTo("UpdatedBrown"))
                .body("totalprice", equalTo(1000));

        Log.info("Validating persisted booking data");

        Response response =
                bookingService.getBooking(String.valueOf(bookingId));

        response.then()
                .statusCode(200)
                .body("firstname", equalTo("UpdatedJim"))
                .body("lastname", equalTo("UpdatedBrown"))
                .body("totalprice", equalTo(1000));

        Log.info("Update Booking test passed");
    }

    @Test(
            description = "API-011: Update Booking (Unauthorized)",
            groups = {"user"}
    )
    public void updateBookingUnauthorizedTest() {

        Log.info("API-011: Starting Unauthorized Update Booking test");

        bookingService = new BookingService();

        String updatePayload = " {\n" +
                "              \"firstname\": \"UnauthorizedJim\"\n" +
                "            }";

        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .body(updatePayload)   // no token
                .when()
                .put("/booking/{id}", bookingId)
                .then()
                .statusCode(403)
                .body(containsString("Forbidden"));

        Log.info("Unauthorized update correctly rejected");


    }
}
