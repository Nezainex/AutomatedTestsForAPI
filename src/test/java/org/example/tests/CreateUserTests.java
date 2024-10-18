package org.example.tests;

import io.qameta.allure.Allure;
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
        Allure.step("Setup API config", ApiConfig::setup);
    }

    @Test
    public void createUserTest() {
        CreateUsersBodyModel user = Allure.step("Generate user data", this::generateUserData);
        Response response = Allure.step("Send create user request", () -> sendCreateUserRequest(user));
        Allure.step("Validate create user response", () -> validateCreateUserResponse(response, user));
    }

    private CreateUsersBodyModel generateUserData() {
        CreateUsersBodyModel user = new CreateUsersBodyModel();
        user.setName(RandomUtils.getRandomName());
        user.setJob(RandomUtils.getRandomJob());
        return user;
    }

    private Response sendCreateUserRequest(CreateUsersBodyModel user) {
        return requestCreateUser.createUser(user);
    }

    private void validateCreateUserResponse(Response response, CreateUsersBodyModel user) {
        Allure.step("Assert create user response", () -> assertions.assertCreateUserResponse(response));
        CreateUserResponseModel createdUser = response.as(CreateUserResponseModel.class);

        Allure.step("Assert user name matches", () -> Assert.assertEquals(createdUser.getName(), user.getName(), "User name doesn't match"));
        Allure.step("Assert user job matches", () -> Assert.assertEquals(createdUser.getJob(), user.getJob(), "User job doesn't match"));
        Allure.step("Assert user ID is not null", () -> Assert.assertNotNull(createdUser.getId(), "User ID is null"));
        Allure.step("Assert creation date format", () -> Assert.assertTrue(DateTimeCheck.isValidDateTimeFormat(createdUser.getCreatedAt()), "Invalid creation date format"));

        Allure.step("Log created user data", () -> logCreatedUserData(createdUser));

        createdUser.setJob("Updated Job");
        createdUser.setCreatedAt("2024-01-01T00:00:00.000Z");

        Allure.step("Assert job update", () -> Assert.assertEquals(createdUser.getJob(), "Updated Job", "Job update doesn't match"));
        Allure.step("Assert creation date update", () -> Assert.assertEquals(createdUser.getCreatedAt(), "2024-01-01T00:00:00.000Z", "CreatedAt update doesn't match"));

        Allure.step("Log updated user data", () -> logUpdatedUserData(createdUser));
    }

    private void logCreatedUserData(CreateUserResponseModel createdUser) {
        System.out.println("Created User ID : " + createdUser.getId());
        System.out.println("Created User at : " + createdUser.getCreatedAt());
    }

    private void logUpdatedUserData(CreateUserResponseModel createdUser) {
        System.out.println("Updated User Job : " + createdUser.getJob());
        System.out.println("Updated CreatedAt : " + createdUser.getCreatedAt());
    }
}