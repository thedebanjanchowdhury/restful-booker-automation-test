package com.api.base;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BaseService {
    // BaseURI
    // Request Creation
    // Response Handling

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private RequestSpecification requestSpecification;

    public BaseService() {
        requestSpecification = given().baseUri(BASE_URL);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected Response getRequest (Object payload, String endpoint) {
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
    }

    protected Response getRequest (String endpoint) {
        return requestSpecification.get(endpoint);
    }

    protected Response getRequestWithParams (String endPoint, Map<String, Object> queryParams ) {
        return requestSpecification.queryParams(queryParams).get(endPoint);
    }
}
