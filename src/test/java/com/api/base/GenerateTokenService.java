package com.api.base;

import com.api.models.request.LoginRequest;
import io.restassured.response.Response;
import utils.Log;

public class GenerateTokenService extends BaseService{

    private static final String BASE_PATH = "/auth";

    public Response login(LoginRequest payload) {
        Log.info("Login Request called");
        return postRequest(payload, BASE_PATH);
    }
}
