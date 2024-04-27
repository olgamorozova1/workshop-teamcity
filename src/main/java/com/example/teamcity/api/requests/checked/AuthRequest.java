package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static org.apache.http.HttpStatus.SC_OK;

public class AuthRequest extends Request {
    public static final String AUTH_ENDPOINT = "/authenticationTest.html?csrf";

    public AuthRequest(RequestSpecification spec) {
        super(spec);
    }

    public String getCsrfToken() {
        return RestAssured
                .given()
                .spec(spec)
                .get(AUTH_ENDPOINT)
                .then().assertThat().statusCode(SC_OK)
                .extract().asString();
    }
}
