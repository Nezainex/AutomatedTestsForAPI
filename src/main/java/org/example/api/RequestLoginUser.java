package org.example.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.example.models.LoginBodyModel;

public class RequestLoginUser {
    public Response loginUser(LoginBodyModel loginModel) {
        return given()
                .contentType(ContentType.JSON)
                .body(loginModel)
                .post("/login")
                .then()
                .extract()
                .response();
    }
}
