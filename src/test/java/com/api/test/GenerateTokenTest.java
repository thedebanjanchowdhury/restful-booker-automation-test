package com.api.test;

import com.api.base.GenerateTokenService;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GenerateTokenTest {

    // Token Generation
    @Test(description = "API-002: Create Auth Token (Happy Path)")
    public void createTokenTest() {
        LoginRequest request = new LoginRequest.Builder().username("admin").password("password123").build();

        GenerateTokenService tokenService = new GenerateTokenService();
        LoginResponse response = tokenService.login(request).as(LoginResponse.class);
        Assert.assertNotNull(response);
    }

    @Test(description = "API-003: Auth Token Failure (Bad Creds)")
    public void authTokenFailureTest() {
        LoginRequest loginRequest = new LoginRequest.Builder().username("debanjan").password("debanjan123").build();
        GenerateTokenService tokenService = new GenerateTokenService();
        tokenService.login(loginRequest).then().log().all();
    }
}

