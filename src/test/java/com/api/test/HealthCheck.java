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
        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .when().get("/ping")
                .then()
                .statusCode(201);
        Log.info("Health Check Completed Successfully");
    }
}
