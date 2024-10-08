package org.example.api;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ApiConfig {
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }
}
