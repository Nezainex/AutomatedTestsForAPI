package org.example.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionsResponseCreateUserApi {
    public void assertCreateUserResponse(Response response) {
        Assert.assertEquals(response.statusCode(), 201, "Status code is not 201");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID is null");
        Assert.assertNotNull(response.jsonPath().getString("createdAt"), "Creation date is null");
    }
}
