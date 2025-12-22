package com.api.test;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheck {

    @Test(description = "API-001: Health Check - /ping returns 201")
    public void healthCheck() {
        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .when().get("/ping")
                .then()
                .log().all()
                .statusCode(201);
    }

}
