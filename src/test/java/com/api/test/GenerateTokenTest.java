package com.api.test;

import com.api.base.GenerateTokenService;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

import static org.hamcrest.Matchers.*;

public class GenerateTokenTest {

    // Token Generation
    @Test(description = "API-002: Create Auth Token (Happy Path)")
    public void createTokenTest() {

        Log.info("Authentication Process Started");
        LoginRequest request = new LoginRequest.Builder().username("admin").password("password123").build();

        GenerateTokenService tokenService = new GenerateTokenService();
        LoginResponse response = tokenService.login(request).as(LoginResponse.class);
        Assert.assertNotNull(response);
        Log.info("Authentication Process Ended, token: " + response.getToken());
    }

    @Test(description = "API-003: Auth Token Failure (Bad Creds)")
    public void authTokenFailureTest() {
        Log.info("Unauthorized Authentication Process Started");
        LoginRequest loginRequest = new LoginRequest.Builder().username("debanjan").password("debanjan123").build();
        GenerateTokenService tokenService = new GenerateTokenService();
        tokenService.login(loginRequest)
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
        Log.info("Unauthorized Authentication Process Ended Successfully");
    }
}

