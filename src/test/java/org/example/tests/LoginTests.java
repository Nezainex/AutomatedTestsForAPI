package org.example.tests;

import io.qameta.allure.Allure;
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
        Allure.step("Setup API config", ApiConfig::setup);
    }

    @Test
    public void loginTest() {
        LoginBodyModel loginBody = Allure.step("Generate login data", this::generateLoginData);
        Response response = Allure.step("Send login request", () -> sendLoginRequest(loginBody));
        Allure.step("Validate login response", () -> validateLoginResponse(response, loginBody));
    }

    private LoginBodyModel generateLoginData() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");
        return loginBody;
    }

    private Response sendLoginRequest(LoginBodyModel loginBody) {
        return requestLoginUser.loginUser(loginBody);
    }

    private void validateLoginResponse(Response response, LoginBodyModel loginBody) {
        LoginResponseModel loginResponse = response.as(LoginResponseModel.class);

        Allure.step("Assert email matches", () -> Assert.assertEquals(loginBody.getEmail(), "eve.holt@reqres.in", "Email doesn't match"));
        Allure.step("Assert password matches", () -> Assert.assertEquals(loginBody.getPassword(), "cityslicka", "Password doesn't match"));

        Allure.step("Assert status code", () -> Assert.assertEquals(response.statusCode(), 200, "Status code is not 200"));
        Allure.step("Assert token is not null", () -> Assert.assertNotNull(loginResponse.getToken(), "Token is null"));

        Allure.step("Log current token", () -> logToken(loginResponse.getToken()));

        String newToken = "new-token-value";
        loginResponse.setToken(newToken);

        Allure.step("Assert token update", () -> Assert.assertEquals(loginResponse.getToken(), newToken, "Token update doesn't match"));
        Allure.step("Log updated token", () -> logUpdatedToken(loginResponse.getToken()));
    }

    private void logToken(String token) {
        System.out.println("Current Token: " + token);
    }

    private void logUpdatedToken(String token) {
        System.out.println("Updated Token: " + token);
    }
}