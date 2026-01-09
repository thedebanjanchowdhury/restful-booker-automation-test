package com.api.test;

import org.testng.annotations.Test;
import utils.Log;

import static io.restassured.RestAssured.*;

public class HealthCheck {

    @Test(
            description = "API-001: Health Check - /ping returns 201",
            groups = {"auth"}
    )
    public void healthCheck() {
        Log.info("API-001: Health Check - /ping returns 201");
        io.restassured.response.Response resp = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .when().get("/ping");

        if (resp.getStatusCode() == 418) {
             String warningMsg = "API Blocked in HealthCheck (418). Skipping.";
             Log.warn(warningMsg);
             throw new org.testng.SkipException(warningMsg);
        }

        resp.then().statusCode(201);
        Log.info("Health Check Completed Successfully");
    }
}
