package org.example.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.example.models.CreateUsersBodyModel;

public class RequestCreateUser {
    public Response createUser(CreateUsersBodyModel userModel) {
        return given()
                .contentType(ContentType.JSON)
                .body(userModel)
                .post("/users")
                .then()
                .extract()
                .response();
    }
}
