package org.example.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.ApiConfig;
import org.example.api.RequestCreateUser;
import org.example.assertions.AssertionsResponseCreateUserApi;
import org.example.models.CreateUserResponseModel;
import org.example.models.CreateUsersBodyModel;
import org.example.utils.DateTimeCheck;
import org.example.utils.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateUserTests {
    RequestCreateUser requestCreateUser = new RequestCreateUser();
    AssertionsResponseCreateUserApi assertions = new AssertionsResponseCreateUserApi();

    @BeforeClass
    public void setup() {
        ApiConfig.setup();
    }

    @Test
    public void createUserTest() {
        CreateUsersBodyModel user = generateUserData();
        Response response = sendCreateUserRequest(user);
        validateCreateUserResponse(response, user);
    }

    @Step("Generate user data with random name and job")
    private CreateUsersBodyModel generateUserData() {
        CreateUsersBodyModel user = new CreateUsersBodyModel();
        user.setName(RandomUtils.getRandomName());
        user.setJob(RandomUtils.getRandomJob());
        return user;
    }

    @Step("Send request to create a new user")
    private Response sendCreateUserRequest(CreateUsersBodyModel user) {
        return requestCreateUser.createUser(user);
    }

    @Step("Validate create user response")
    private void validateCreateUserResponse(Response response, CreateUsersBodyModel user) {
        assertions.assertCreateUserResponse(response);
        CreateUserResponseModel createdUser = response.as(CreateUserResponseModel.class);

        Assert.assertEquals(createdUser.getName(), user.getName(), "User name doesn't match");
        Assert.assertEquals(createdUser.getJob(), user.getJob(), "User job doesn't match");
        Assert.assertNotNull(createdUser.getId(), "User ID is null");
        Assert.assertTrue(DateTimeCheck.isValidDateTimeFormat(createdUser.getCreatedAt()), "Invalid creation date format");

        logCreatedUserData(createdUser);

        // Modify and verify job and creation date
        createdUser.setJob("Updated Job");
        createdUser.setCreatedAt("2024-01-01T00:00:00.000Z");

        Assert.assertEquals(createdUser.getJob(), "Updated Job", "Job update doesn't match");
        Assert.assertEquals(createdUser.getCreatedAt(), "2024-01-01T00:00:00.000Z", "CreatedAt update doesn't match");

        logUpdatedUserData(createdUser);
    }

    @Step("Log created user data")
    private void logCreatedUserData(CreateUserResponseModel createdUser) {
        System.out.println("Created User ID : " + createdUser.getId());
        System.out.println("Created User at : " + createdUser.getCreatedAt());
    }

    @Step("Log updated user data")
    private void logUpdatedUserData(CreateUserResponseModel createdUser) {
        System.out.println("Updated User Job : " + createdUser.getJob());
        System.out.println("Updated CreatedAt : " + createdUser.getCreatedAt());
    }
}
