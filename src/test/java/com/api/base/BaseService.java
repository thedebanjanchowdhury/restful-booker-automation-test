package com.api.base;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BaseService {
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private RequestSpecification requestSpecification;

    public BaseService() {
        requestSpecification = given().baseUri(BASE_URL);
    }

    protected RequestSpecification getNewSpec() {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return getNewSpec().body(payload).post(endpoint);
    }

    protected Response getRequest (Object payload, String endpoint) {
        return getNewSpec().body(payload).post(endpoint);
    }

    protected Response getRequest (String endpoint) {
        return getNewSpec().get(endpoint);
    }

    protected Response getRequestWithParams (String endPoint, Map<String, Object> queryParams ) {
        return getNewSpec().queryParams(queryParams).get(endPoint);
    }

    protected Response putRequest (Object payload, String endpoint, String token) {
        return getNewSpec().cookie("token",token).body(payload).put(endpoint);
    }

    protected Response putRequestDenial (Object payload, String endpoint, String token) {
        return getNewSpec().cookie("token",token).body(payload).put(endpoint);
    }

    protected Response patchRequest (Object payload, String endpoint, String token) {
        return getNewSpec().cookie("token",token).body(payload).patch(endpoint);
    }

    protected Response deleteRequest (String endpoint, String token) {
        return getNewSpec().cookie("token", token).delete(endpoint);
    }

    protected Response deleteRequestUnauthorized (String endpoint) {
        return getNewSpec().delete(endpoint);
    }
}
