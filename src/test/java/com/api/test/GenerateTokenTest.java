package com.api.test;

import com.api.base.GenerateTokenService;
import com.api.models.request.LoginRequest;
import com.api.models.resoponse.LoginResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GenerateTokenTest {

    @Test(description = "Test functionality of login")
    public void loginTest() {

        LoginRequest loginRequest = new LoginRequest("admin", "password123");
        GenerateTokenService tokenService = new GenerateTokenService();
        Response response = tokenService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        System.out.println(response.asPrettyString());

        Assert.assertNotNull(loginResponse.getToken());
    }
}

