package org.example.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.ApiConfig;
import org.example.api.RequestLoginUser;
import org.example.models.LoginBodyModel;
import org.example.models.LoginResponseModel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTests {
    RequestLoginUser requestLoginUser = new RequestLoginUser();

    @BeforeClass
    public void setup() {
        ApiConfig.setup();
    }

    @Test
    public void loginTest() {
        LoginBodyModel loginBody = generateLoginData();
        Response response = sendLoginRequest(loginBody);
        validateLoginResponse(response, loginBody);
    }

    @Step("Generate login data with email and password")
    private LoginBodyModel generateLoginData() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");
        return loginBody;
    }

    @Step("Send request to login user")
    private Response sendLoginRequest(LoginBodyModel loginBody) {
        return requestLoginUser.loginUser(loginBody);
    }

    @Step("Validate login response")
    private void validateLoginResponse(Response response, LoginBodyModel loginBody) {
        LoginResponseModel loginResponse = response.as(LoginResponseModel.class);

        Assert.assertEquals(loginBody.getEmail(), "eve.holt@reqres.in", "Email doesn't match");
        Assert.assertEquals(loginBody.getPassword(), "cityslicka", "Password doesn't match");

        Assert.assertEquals(response.statusCode(), 200, "Status code is not 200");
        Assert.assertNotNull(loginResponse.getToken(), "Token is null");

        // Log token and update
        logToken(loginResponse.getToken());

        String newToken = "new-token-value";
        loginResponse.setToken(newToken);

        Assert.assertEquals(loginResponse.getToken(), newToken, "Token update doesn't match");
        logUpdatedToken(loginResponse.getToken());
    }

    @Step("Log current token")
    private void logToken(String token) {
        System.out.println("Current Token: " + token);
    }

    @Step("Log updated token")
    private void logUpdatedToken(String token) {
        System.out.println("Updated Token: " + token);
    }
}
