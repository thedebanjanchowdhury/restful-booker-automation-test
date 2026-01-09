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
    @Test(
            description = "API-002: Create Auth Token (Happy Path)",
            groups = {"auth"}
    )
    public void createTokenTest() {

        Log.info("Authentication Process Started");
        LoginRequest request = new LoginRequest.Builder().username("admin").password("password123").build();

        GenerateTokenService tokenService = new GenerateTokenService();
        Response resp = tokenService.login(request);
        
        if (resp.getStatusCode() == 418) {
            String warningMsg = "API Blocked (418 I'm a teapot). Skipping test.";
            Log.warn(warningMsg);
            throw new org.testng.SkipException(warningMsg);
        }
        
        if (resp.getStatusCode() != 200) {
             String errorMsg = "Login failed in test. Status: " + resp.getStatusLine() + ", Body: " + resp.asString();
             Log.error(errorMsg);
             Assert.fail(errorMsg); // Fail explicitly if not 418 but not 200
        }

        LoginResponse response = resp.as(LoginResponse.class);
        Assert.assertNotNull(response);
        Log.info("Authentication Process Ended, token: " + response.getToken());
    }

    @Test(
            description = "API-003: Auth Token Failure (Bad Creds)",
            groups = {"auth"}
    )
    public void authTokenFailureTest() {
        Log.info("Unauthorized Authentication Process Started");
        LoginRequest loginRequest = new LoginRequest.Builder().username("debanjan").password("debanjan123").build();
        GenerateTokenService tokenService = new GenerateTokenService();
        Response resp = tokenService.login(loginRequest);
        
        if (resp.getStatusCode() == 418) {
            String warningMsg = "API Blocked in auth failure test (418). Skipping.";
            Log.warn(warningMsg);
            throw new org.testng.SkipException(warningMsg);
        }

        resp.then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
        Log.info("Unauthorized Authentication Process Ended Successfully");
    }
}

