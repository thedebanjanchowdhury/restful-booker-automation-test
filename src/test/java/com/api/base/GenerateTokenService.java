package com.api.base;

import com.api.models.request.LoginRequest;
import io.restassured.response.Response;

public class GenerateTokenService extends BaseService{

    private static final String BASE_PATH = "/auth";

    public Response login(LoginRequest payload) {
        return postRequest(payload, BASE_PATH);
    }
}
