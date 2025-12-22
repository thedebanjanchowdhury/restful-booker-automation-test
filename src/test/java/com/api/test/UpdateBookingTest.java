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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateBookingTest {

    private BookingService bookingService;
    private String token;
    private int bookingId;

    @BeforeMethod
    public void setup() {
        LoginRequest request = new LoginRequest.Builder().username("admin").password("password123").build();
        GenerateTokenService generateTokenService = new GenerateTokenService();
        LoginResponse loginResponse = generateTokenService.login(request).as(LoginResponse.class);
        token = loginResponse.getToken();
        System.out.println("token: " + token);

        GetBookingIDService getBookingIDService = new GetBookingIDService();
        Response idResponse = getBookingIDService.getAllBookings();
        bookingId = idResponse.jsonPath().getInt("bookingid[1]");
    }

    @Test(description = "API-007: Update Booking Check")
    public void updateBookingTest() throws Exception{

        bookingService = new BookingService();
        BookingRequest bookingRequest = new BookingRequest.Builder()
                .firstname("UpdatedJim")
                .lastname("UpdatedBrown")
                .totalprice(1000)
                .depositpaid(false)
                .bookingdates(new BookingDates("2025-12-25","2025-12-30"))
                .additionalneeds("Dinner")
                .build();

        Response updateRepsonse = bookingService
                .updateBooking(bookingRequest,token,String.valueOf(bookingId));
        updateRepsonse.then().statusCode(200)
                .body("firstname", equalTo("UpdatedJim"))
                .body("lastname", equalTo("UpdatedBrown"))
                .body("totalprice", equalTo(1000));

        Response response = bookingService.getBooking(String.valueOf(bookingId));
        response.then().statusCode(200)
        .body("firstname", equalTo("UpdatedJim"))
        .body("lastname", equalTo("UpdatedBrown"))
        .body("totalprice", equalTo(1000));
    }

    @Test(description = "API-008: Update Booking (Unauthorized)")
    public void updateBookingUnauthorizedTest(){
        bookingService = new BookingService();
        String updatePayload = " {\n" +
                "              \"firstname\": \"UnauthorizedJim\"\n" +
                "            }";

        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .contentType("application/json")
                .body(updatePayload)  // NO TOKEN
                .when()
                .put("/booking/{id}", bookingId)
                .then()
                .statusCode(403)  // Forbidden without token
                .body(containsString("Forbidden"));
    }
}
